package exercise.exercise.domain.service;

import java.util.UUID;

import exercise.exercise.consts.Consts;
import exercise.exercise.domain.model.GameStateEnum;
import exercise.exercise.consts.RoleEnum;
import exercise.exercise.domain.model.Game;
import exercise.exercise.domain.utility.CreateGame;
import exercise.exercise.domain.utility.Minimax;
import exercise.exercise.domain.utility.Validate;

/**
 * Реализация сервиса для игры в крестики-нолики.
 * Содержит методы для обработки ходов, проверки состояния игры и управления
 * игроками.
 */
public class TicTacToeServiceImpl implements TicTacToeService {
  private UserService userService;

  /**
   * Конструктор, инициализирующий сервис с использованием UserService.
   *
   * @param userService сервис для управления пользователями
   */
  public TicTacToeServiceImpl(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Game getNextMove(Game game, Game oldGame) {
    try {
      if (isValidGame(game, oldGame) && !game.isGameOver())
        processGame(game, oldGame);
      else
        throw new IllegalArgumentException("Некорректное поле");

    } catch (Exception e) {
      throw e;
    }

    return game;
  }

  @Override
  public boolean isValidGame(Game newStepGame, Game oldGame) {
    return Validate.validateGame(newStepGame, oldGame, userService.authorize(newStepGame.getAuthHeader()));
  }

  @Override
  public boolean isGameOver(Game game) {
    return Validate.validateGameOver(game);
  }

  @Override
  public Game joinPlayer(Game userGame, Game oldGame) {
    UUID user = userService.authorize(userGame.getAuthHeader());

    if (oldGame.getPlayer1() == null) {
      oldGame.setPlayer1(user);
      oldGame.setCurrentPlayer(user);

    } else {
      oldGame.setPlayer2(user);
    }

    oldGame.setState(GameStateEnum.PLAYER_TURN);

    return oldGame;
  }

  @Override
  public Game createGame(Game userGame, Game oldGame) {
    UUID user = userService.authorize(userGame.getAuthHeader());
    return CreateGame.createGame(userGame, oldGame, user);
  }

  /**
   * Выполняет ход компьютера, используя алгоритм Minimax.
   *
   * @param game    текущее состояние игры
   * @param oldGame предыдущее состояние игры
   * @throws IllegalArgumentException если ход компьютера некорректен
   */
  private void minimax(Game game, Game oldGame) {
    int[] computerMove = new Minimax().findOptimalMovement(game.getBoard().getBoard());

    if (!Validate.validateMove(computerMove, RoleEnum.PLAYER2,
        game.getBoard().getBoard()[computerMove[0]][computerMove[1]]))
      throw new IllegalArgumentException("Некорректный ход компьютера");

    else
      game.getBoard().setElement(computerMove[0], computerMove[1],
          oldGame.getPlayer1().equals(UUID.fromString("00000000-0000-0000-0000-000000000000")) ? 1 : 2);

    changeTurn(game);
  }

  /**
   * Определяет победителя игры на основе состояния доски.
   *
   * @param board текущее состояние доски
   * @return состояние игры, определяющее победителя
   */
  private static GameStateEnum getWinner(int[][] board) {
    RoleEnum findWiner = RoleEnum.NONE;

    for (int i = 0; i < Consts.SIZE && findWiner == RoleEnum.NONE; i++)
      if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2])
        findWiner = RoleEnum.values()[board[i][0]];

    for (int i = 0; i < Consts.SIZE && findWiner == RoleEnum.NONE; i++)
      if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i])
        findWiner = RoleEnum.values()[board[0][i]];

    if (findWiner == RoleEnum.NONE)
      if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2])
        findWiner = RoleEnum.values()[board[0][0]];
      else if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0])
        findWiner = RoleEnum.values()[board[0][2]];

    return findWiner == RoleEnum.NONE ? GameStateEnum.NO_ONE_WON
        : findWiner == RoleEnum.PLAYER1 ? GameStateEnum.PLAYER1_WON
            : GameStateEnum.PLAYER2_WON;
  }

  /**
   * Меняет текущего игрока на следующего.
   *
   * @param game текущее состояние игры
   */
  private void changeTurn(Game game) {
    game.setCurrentPlayer(game.getCurrentPlayer().equals(game.getPlayer1())
        ? game.getPlayer2()
        : game.getPlayer1());
  }

  /**
   * Обрабатывает ход игры, переключая игрока и выполняя ход компьютера, если
   * необходимо.
   *
   * @param game    текущее состояние игры
   * @param oldGame предыдущее состояние игры
   */
  private void processGame(Game game, Game oldGame) {
    changeTurn(game);
    UUID comp = UUID.fromString("00000000-0000-0000-0000-000000000000");

    if (game.getCurrentPlayer().equals(comp))
      minimax(game, oldGame);

    if (isGameOver(game))
      gameOver(game);
  }

  /**
   * Завершает игру, устанавливая ее состояние и определяя победителя.
   *
   * @param game текущее состояние игры
   */
  private void gameOver(Game game) {
    game.setGameOver(true);
    game.setState(getWinner(game.getBoard().getBoard()));
    game.setCurrentPlayer(game.getState() == GameStateEnum.PLAYER1_WON
        ? game.getPlayer1()
        : game.getState() == GameStateEnum.PLAYER2_WON
            ? game.getPlayer2()
            : null);
  }
}