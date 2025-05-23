package exercise.exercise.datasource.service;

import exercise.exercise.datasource.model.GameEntity;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
/**
 * Репозиторий для управления сущностями игры.
 * Обеспечивает доступ к данным игры через интерфейс GameCrudRepository.
 */
@Repository
public class GameRepository {
    private final GameCrudRepository gameCrudRepository;
    /**
     * Конструктор, инициализирующий репозиторий с использованием GameCrudRepository.
     *
     * @param gameCrudRepository интерфейс для работы с CRUD-операциями над сущностями игры
     */
    public GameRepository(GameCrudRepository gameCrudRepository) {
        this.gameCrudRepository = gameCrudRepository;
    }
    /**
     * Получает игру по уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор игры
     * @return объект Optional, содержащий игру, если она найдена, иначе пустой объект
     */
    public Optional<GameEntity> getGameByUUID(String uuid) {
        return gameCrudRepository.findById(uuid);
    }
    /**
     * Сохраняет сущность игры в репозитории.
     *
     * @param game сущность игры для сохранения
     * @return сохраненная сущность игры
     */
    public GameEntity saveGame(GameEntity game) {
        gameCrudRepository.save(game);
        return game;
    }
    /**
     * Получает список всех игр в виде списка уникальных идентификаторов.
     *
     * @return список UUID всех игр
     */
    public List<UUID> getAllGames() {
        return StreamSupport.stream(gameCrudRepository.findAll().spliterator(), false)
                .map(game -> UUID.fromString(game.getGameId()))
                .toList();
    }
    /**
     * Получает список доступных игр для данного игрока по его уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор игрока
     * @return список UUID доступных игр
     */
    public List<UUID> getAvailableGames(UUID uuid) {
        return StreamSupport.stream(gameCrudRepository.findAll().spliterator(), false)
                .filter(game -> (game.getPlayer1() != null ^ game.getPlayer2() != null))
                .filter(game -> (game.getPlayer1() != null && !game.getPlayer1().equals(uuid))
                        || (game.getPlayer2() != null && !game.getPlayer2().equals(uuid)))
                .map(game -> UUID.fromString(game.getGameId()))
                .toList();
    }
}