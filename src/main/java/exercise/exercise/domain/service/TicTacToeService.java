package exercise.exercise.domain.service;

import exercise.exercise.domain.model.Game;

/**
 * Интерфейс, определяющий сервис для игры в крестики-нолики.
 * Содержит методы для управления игровым процессом, проверки ходов и состояния игры.
 */
public interface TicTacToeService {

  /**
   * Получает следующий ход для игры на основе текущего состояния игры и предыдущего состояния.
   *
   * @param game текущее состояние игры
   * @param oldGame предыдущее состояние игры
   * @return обновленное состояние игры с учетом следующего хода
   */
  Game getNextMove(Game game, Game oldGame);
  /**
   * Проверяет, является ли новый ход допустимым в рамках текущего состояния игры.
   *
   * @param newStepGame новое состояние игры с предложенным ходом
   * @param oldGame предыдущее состояние игры
   * @return true, если ход допустим, иначе false
   */
  boolean isValidGame(Game newStepGame, Game oldGame);
  /**
   * Проверяет, завершена ли игра.
   *
   * @param game текущее состояние игры
   * @return true, если игра завершена, иначе false
   */
  boolean isGameOver(Game game);
  /**
   * Позволяет игроку присоединиться к текущей игре.
   *
   * @param userGame состояние игры для пользователя
   * @param oldGame предыдущее состояние игры
   * @return обновленное состояние игры с учетом нового игрока
   */
  Game joinPlayer(Game userGame, Game oldGame);
  /**
   * Создает новую игру на основе состояния, предоставленного пользователем.
   *
   * @param userGame состояние игры для пользователя
   * @param oldGame предыдущее состояние игры
   * @return состояние новой игры
   */
  Game createGame(Game userGame, Game oldGame);
}