package exercise.exercise.datasource.mapper;

import exercise.exercise.datasource.model.TicTacToeBoardEntity;
import exercise.exercise.domain.model.Game;
import exercise.exercise.datasource.model.GameEntity;
import exercise.exercise.domain.model.TicTacToeBoard;

import java.util.Optional;
import java.util.stream.IntStream;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
/**
 * Интерфейс для преобразования между сущностями игры и их представлениями.
 * Использует MapStruct для автоматического создания реализаций мапперов.
 */
@Mapper
public interface GameMapperData {
    GameMapperData INSTANCE = Mappers.getMapper(GameMapperData.class);
    /**
     * Преобразует сущность игры (GameEntity) в объект игры (Game).
     * Некоторые поля игнорируются при преобразовании.
     *
     * @param gameEntity сущность игры для преобразования
     * @return объект игры
     */
    @Mapping(target = "authHeader", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "move", ignore = true)
    @Mapping(target = "winner", ignore = true)
    Game fromData(GameEntity gameEntity);
    /**
     * Преобразует объект игры (Game) в сущность игры (GameEntity).
     *
     * @param game объект игры для преобразования
     * @return сущность игры
     */
    @Mapping(target = "board", source = "board")
    GameEntity toData(Game game);
    /**
     * Преобразует объект Optional<Game> в Optional<GameEntity>.
     *
     * @param game объект Optional, содержащий объект игры
     * @return объект Optional, содержащий сущность игры
     */
    default Optional<GameEntity> toData(Optional<Game> game) {
        return game.map(this::toData);
    }
    /**
     * Преобразует объект Optional<GameEntity> в объект Game.
     *
     * @param gameEntity объект Optional, содержащий сущность игры
     * @return объект игры или null, если gameEntity пустой
     */
    default Game fromData(Optional<GameEntity> gameEntity) {
        return gameEntity.map(this::fromData).orElse(null);
    }
    /**
     * Преобразует сущность доски для игры в крестики-нолики (TicTacToeBoardEntity) в объект доски игры (TicTacToeBoard).
     *
     * @param store сущность доски для игры
     * @return объект доски игры
     */
    default TicTacToeBoard map(TicTacToeBoardEntity store) {
        TicTacToeBoard game = new TicTacToeBoard();
        IntStream.range(0, store.getRows())
                .forEachOrdered(i -> IntStream.range(0, store.getCols())
                        .forEachOrdered(j -> game.setElement(i, j, store.getElement(i, j))));
        return game;
    }
    /**
     * Преобразует объект доски игры (TicTacToeBoard) в сущность доски для игры (TicTacToeBoardEntity).
     *
     * @param game объект доски игры для преобразования
     * @return сущность доски для игры
     */
    default TicTacToeBoardEntity map(TicTacToeBoard game) {
        TicTacToeBoardEntity store = new TicTacToeBoardEntity(new int[game.getRows()][game.getCols()]);
        IntStream.range(0, game.getRows())
                .forEachOrdered(i -> IntStream.range(0, game.getCols())
                        .forEachOrdered(j -> store.setElement(i, j, game.getElement(i, j))));
        return store;
    }
}