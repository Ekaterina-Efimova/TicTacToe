package exercise.exercise.datasource.model;

import exercise.exercise.datasource.mapper.TicTacToeBoardMapperData;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая игру в базе данных.
 * Содержит информацию о состоянии игры, игроках и текущем ходе.
 */
@Getter
@Setter
@Entity
@Table(name = "games")
public class GameEntity {

  /**
   * Уникальный идентификатор игры.
   */
  @Id
  private String gameId;
  /**
   * Доска игры, представляющая состояние игры в формате TicTacToeBoardEntity.
   * Преобразуется в строку JSON для хранения в базе данных.
   */
  @Convert(converter = TicTacToeBoardMapperData.class)
  @Column(name = "board")
  private TicTacToeBoardEntity board;
  /**
   * Идентификатор первого игрока.
   */
  @Column(name = "player1")
  private UUID player1;
  /**
   * Идентификатор второго игрока.
   */
  @Column(name = "player2")
  private UUID player2;
  /**
   * Флаг, указывающий, завершена ли игра.
   */
  @Column(name = "gameOver")
  private boolean isGameOver;
  /**
   * Идентификатор текущего игрока, чей ход.
   */
  @Column(name = "currentPlayer")
  private UUID currentPlayer;
  /**
   * Флаг, указывающий, является ли игра одиночной (single player).
   */
  @Column(name = "singlePlayer")
  private boolean singlePlayer;
}