package exercise.exercise.web.controller.utils;
import exercise.exercise.datasource.mapper.GameMapperData;
import exercise.exercise.di.DependencyGraphConfiguration;
import exercise.exercise.web.mapper.GameMapperWeb;
import exercise.exercise.web.model.GameWeb;
import java.util.UUID;
/**
 * Утилитный класс для работы с игровыми объектами и их преобразованиями.
 * Содержит методы для создания игры, сохранения новой игры, обработки следующего хода
 * и поиска игры по уникальному идентификатору.
 */
public class HelpingUtils {
    /**
     * Создает новый объект игры с заданными параметрами.
     *
     * @param authHeader заголовок авторизации
     * @param isSinglePlayer флаг, указывающий, является ли игра одиночной
     * @param gameId уникальный идентификатор игры (может быть null для генерации нового)
     * @return созданный объект GameWeb
     */
    public static GameWeb createGame(String authHeader, boolean isSinglePlayer, UUID gameId) {
        GameWeb game = new GameWeb();
        game.setGameId(gameId == null ? UUID.randomUUID() : gameId);
        game.setSinglePlayer(isSinglePlayer);
        game.setAuthHeader(authHeader);
        return game;
    }
    /**
     * Сохраняет новую игру в репозитории и возвращает объект GameWeb.
     *
     * @param context контекст зависимостей для доступа к сервисам
     * @param game объект GameWeb для сохранения
     * @return сохраненный объект GameWeb
     */
    public static GameWeb saveNewGame(DependencyGraphConfiguration context, GameWeb game) {
        return GameMapperWeb.INSTANCE.toWeb(
                GameMapperData.INSTANCE.fromData(
                        context.gameRepositoryService().saveGame(
                                GameMapperData.INSTANCE.toData(
                                        context.ticTacToeService().createGame(
                                                GameMapperWeb.INSTANCE.fromWeb(game), null)))));
    }
    /**
     * Обрабатывает следующий ход в игре и возвращает обновленный объект GameWeb.
     *
     * @param context контекст зависимостей для доступа к сервисам
     * @param game объект GameWeb, представляющий текущее состояние игры
     * @param gameId уникальный идентификатор игры
     * @return обновленный объект GameWeb после следующего хода
     */
    public static GameWeb nextMove(DependencyGraphConfiguration context, GameWeb game, UUID gameId) {
        return GameMapperWeb.INSTANCE.toWeb(
                GameMapperData.INSTANCE.fromData(
                        context.gameRepositoryService().saveGame(
                                GameMapperData.INSTANCE.toData(
                                        context.ticTacToeService().getNextMove(
                                                GameMapperWeb.INSTANCE.fromWeb(game),
                                                GameMapperData.INSTANCE.fromData(
                                                        context.gameRepositoryService().getGameByUUID(gameId.toString())))))));
    }
    /**
     * Находит игру по уникальному идентификатору и возвращает соответствующий объект GameWeb.
     *
     * @param context контекст зависимостей для доступа к сервисам
     * @param gameId уникальный идентификатор игры
     * @return объект GameWeb, представляющий найденную игру
     */
    public static GameWeb findGame(DependencyGraphConfiguration context, UUID gameId) {
        return GameMapperWeb.INSTANCE.toWeb(
                GameMapperData.INSTANCE.fromData(
                        context.gameRepositoryService().getGameByUUID(gameId.toString())));
    }
}