package exercise.exercise.web.controller;

import exercise.exercise.consts.Consts;
import exercise.exercise.di.DependencyGraphConfiguration;
import exercise.exercise.datasource.mapper.GameMapperData;
import exercise.exercise.web.controller.utils.HelpingUtils;
import exercise.exercise.web.mapper.GameMapperWeb;
import exercise.exercise.web.mapper.UserMapperWeb;
import exercise.exercise.web.model.GameWeb;
import exercise.exercise.web.model.UserWeb;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Контроллер для управления играми.
 * Обрабатывает HTTP-запросы, связанные с созданием игр, ходами, получением информации об играх и пользователях.
 */
@RestController
@RequestMapping(Consts.GAME_PATH)
public class GameController {
    private final DependencyGraphConfiguration context;
    /**
     * Конструктор, инициализирующий контроллер с контекстом зависимостей.
     *
     * @param context контекст зависимостей для доступа к сервисам
     */
    @Autowired
    public GameController(DependencyGraphConfiguration context) {
        this.context = context;
    }
    /**
     * Создает новую игру.
     *
     * @param authHeader заголовок авторизации
     * @param isSinglePlayer флаг, указывающий, является ли игра одиночной
     * @return ResponseEntity с созданной игрой и статусом 201 (Created) или 400 (Bad Request) в случае ошибки
     * @throws URISyntaxException если URI не может быть сформирован
     */
    @PostMapping(Consts.GAME_NEW_PATH)
    public ResponseEntity<GameWeb> createGame(@RequestHeader("Authorization") String authHeader,
                                              @RequestParam boolean isSinglePlayer) throws URISyntaxException {
        try {
            GameWeb newGame = HelpingUtils.saveNewGame(context, HelpingUtils.createGame(authHeader, isSinglePlayer, null));
            return ResponseEntity.created(new URI(Consts.GAME_PATH + newGame.gameId.toString())).body(newGame);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    /**
     * Обрабатывает следующий ход в игре.
     *
     * @param gameId уникальный идентификатор игры
     * @param authHeader заголовок авторизации
     * @param game объект GameWeb, представляющий текущее состояние игры
     * @return ResponseEntity с обновленным состоянием игры и статусом 200 (OK) или соответствующим статусом ошибки
     */
    @PostMapping(Consts.GAME_ID_PATH)
    public ResponseEntity<GameWeb> nextMove(@PathVariable UUID gameId,
                                            @RequestHeader("Authorization") String authHeader,
                                            @RequestBody GameWeb game) {
        game.setGameId(gameId);
        game.setAuthHeader(authHeader);
        try {
            return ResponseEntity.ok(HelpingUtils.nextMove(context, game, gameId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    /**
     * Получает игру по уникальному идентификатору.
     *
     * @param gameId уникальный идентификатор игры
     * @return ResponseEntity с объектом GameWeb и статусом 200 (OK) или 404 (Not Found), если игра не найдена
     */
    @GetMapping(Consts.GAME_ID_PATH)
    public ResponseEntity<GameWeb> getGameByUUID(@PathVariable UUID gameId) {
        GameWeb game = HelpingUtils.findGame(context, gameId);
        return game != null ? ResponseEntity.ok(game) : ResponseEntity.notFound().build();
    }
    /**
     * Присоединяется к игре по уникальному идентификатору.
     *
     * @param gameId уникальный идентификатор игры
     * @param authHeader заголовок авторизации
     * @return ResponseEntity с обновленным состоянием игры и статусом 200 (OK) или 404 (Not Found), если игра не найдена
     */
    @PatchMapping(Consts.GAME_ID_PATH)
    public ResponseEntity<GameWeb> joinGameByUUID(@PathVariable UUID gameId,
                                                  @RequestHeader("Authorization") String authHeader) {
        GameWeb oldGameWeb = HelpingUtils.findGame(context, gameId);
        GameWeb game = HelpingUtils.createGame(authHeader, false, gameId);
        return oldGameWeb != null
                ? ResponseEntity.ok(GameMapperWeb.INSTANCE.toWeb(
                GameMapperData.INSTANCE.fromData(
                        context.gameRepositoryService().saveGame(
                                GameMapperData.INSTANCE.toData(
                                        context.ticTacToeService().joinPlayer(GameMapperWeb.INSTANCE.fromWeb(game),
                                                GameMapperWeb.INSTANCE.fromWeb(oldGameWeb)))))))
                : ResponseEntity.notFound().build();
    }
    /**
     * Получает информацию о пользователе по его уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор пользователя
     * @return ResponseEntity с объектом UserWeb и статусом 200 (OK) или 404 (Not Found), если пользователь не найден
     */
    @GetMapping(Consts.USER_INFO_PATH)
    public ResponseEntity<Optional<UserWeb>> getUserByUUID(@PathVariable UUID uuid) {
        Optional<UserWeb> user = UserMapperWeb.INSTANCE.toWeb(context.userService().getUserByUUID(uuid));
        return user.isPresent() ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    /**
     * Получает список всех игр.
     *
     * @param authHeader заголовок авторизации
     * @return ResponseEntity со списком идентификаторов всех игр
     */
    @GetMapping(Consts.ALL_GAME_PATH)
    public ResponseEntity<List<UUID>> getAllGames(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(context.gameRepositoryService().getAllGames());
    }
    /**
     * Получает список доступных игр для текущего пользователя.
     *
     * @param authHeader заголовок авторизации
     * @return ResponseEntity со списком идентификаторов доступных игр
     */
    @GetMapping(Consts.AVAILABLE_PATH)
    public ResponseEntity<List<UUID>> getAvailableGames(@RequestHeader("Authorization") String authHeader) {
        List<UUID> availableGames = context.gameRepositoryService().getAvailableGames(
                context.userService().authorize(authHeader));
        return ResponseEntity.ok(availableGames);
    }
}