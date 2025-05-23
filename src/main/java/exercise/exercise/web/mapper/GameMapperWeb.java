package exercise.exercise.web.mapper;
import exercise.exercise.domain.model.Game;
import exercise.exercise.domain.model.TicTacToeBoard;
import exercise.exercise.web.model.GameWeb;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
/**
 * Интерфейс для преобразования между доменными моделями игры и веб-моделями.
 * Использует MapStruct для автоматического создания реализаций мапперов.
 */
@Mapper
public interface GameMapperWeb {
  GameMapperWeb INSTANCE = Mappers.getMapper(GameMapperWeb.class);
  /**
   * Преобразует доменную модель игры (Game) в веб-модель игры (GameWeb).
   *
   * @param game доменная модель игры для преобразования
   * @return веб-модель игры
   */
  @Mapping(target = "gameId", expression = "java(toUUID(game.getGameId().toString()))")
  @Mapping(target = "info", ignore = true)
  GameWeb toWeb(Game game);
  /**
   * Преобразует объект Optional<Game> в Optional<GameWeb>.
   *
   * @param game объект Optional, содержащий доменную модель игры
   * @return объект Optional, содержащий веб-модель игры
   */
  default Optional<GameWeb> toWeb(Optional<Game> game) {
    return game.map(this::toWeb);
  }
  /**
   * Преобразует веб-модель игры (GameWeb) обратно в доменную модель игры (Game).
   *
   * @param gameWeb веб-модель игры для преобразования
   * @return доменная модель игры
   */
  @InheritInverseConfiguration
  @Mapping(target = "gameOver", ignore = true)
  @Mapping(target = "board", source = "board")
  Game fromWeb(GameWeb gameWeb);
  /**
   * Преобразует строковое представление UUID в объект UUID.
   *
   * @param uuid строковое представление UUID
   * @return объект UUID или null, если uuid равен null
   */
  default UUID toUUID(String uuid) {
    return uuid == null ? null : UUID.fromString(uuid);
  }
  /**
   * Преобразует объект UUID в строковое представление.
   *
   * @param uuid объект UUID
   * @return строковое представление UUID или null, если uuid равен null
   */
  default String fromUUID(UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }
  /**
   * Преобразует объект TicTacToeBoard в двумерный массив целых чисел.
   *
   * @param board объект TicTacToeBoard для преобразования
   * @return двумерный массив целых чисел, представляющий доску
   */
  default int[][] map(TicTacToeBoard board) {
    int[][] array = new int[board.getRows()][board.getCols()];
    IntStream.range(0, board.getRows())
            .forEach(i -> IntStream.range(0, board.getCols())
                    .forEach(j -> array[i][j] = board.getElement(i, j)));
    return array;
  }
  /**
   * Преобразует двумерный массив целых чисел в объект TicTacToeBoard.
   *
   * @param array двумерный массив целых чисел для преобразования
   * @return объект TicTacToeBoard
   */
  default TicTacToeBoard map(int[][] array) {
    TicTacToeBoard board = new TicTacToeBoard();
    IntStream.range(0, array.length)
            .forEach(i -> IntStream.range(0, array[i].length)
                    .forEach(j -> board.setElement(i, j, array[i][j])));
    return board;
  }
}