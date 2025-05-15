package exercise.exercise.domain.utility;

import exercise.exercise.consts.Consts;
import exercise.exercise.domain.model.other.RoleEnum;
import exercise.exercise.domain.model.Game;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Утилитный класс для валидации ходов и состояния игры в крестики-нолики.
 */
public class Validate {
    /**
     * Проверяет, является ли ход допустимым.
     *
     * @param move массив, содержащий координаты хода (строка, столбец)
     * @param role роль игрока, выполняющего ход
     * @param value значение, представляющее текущий ход
     * @return true, если ход допустим; иначе false
     */
    public static boolean validateMove(int[] move, RoleEnum role, int value) {
        return !(move == null || move[0] < 0 || move[0] > 2 || move[1] < 0
                || move[1] > 2) || RoleEnum.checkRole(value);
    }
    /**
     * Проверяет, завершена ли игра.
     *
     * @param game объект игры для проверки
     * @return true, если игра завершена; иначе false
     */
    public static boolean validateGameOver(Game game) {
        boolean result = true;
        for (int i = 0; i < Consts.SIZE && result; i++) {
            for (int j = 0; j < Consts.SIZE && result; j++) {
                if (game.getBoard().getElement(i, j) == RoleEnum.NONE.ordinal()) {
                    result = false;
                }
            }
        }
        for (int i = 0; i < Consts.SIZE && !result; i++)
            if ((game.getBoard().getElement(i, 0) != RoleEnum.NONE.ordinal()
                    && game.getBoard().getElement(i, 0) == game.getBoard().getElement(i, 1)
                    && game.getBoard().getElement(i, 1) == game.getBoard().getElement(i, 2))
                    || (game.getBoard().getElement(0, i) != RoleEnum.NONE.ordinal()
                    && game.getBoard().getElement(0, i) == game.getBoard().getElement(1, i)
                    && game.getBoard().getElement(1, i) == game.getBoard().getElement(2, i)))
                result = true;
        if (!result
                && (game.getBoard().getElement(0, 0) != RoleEnum.NONE.ordinal()
                && game.getBoard().getElement(0, 0) == game.getBoard().getElement(1, 1)
                && game.getBoard().getElement(1, 1) == game.getBoard().getElement(2, 2))
                || (game.getBoard().getElement(0, 2) != RoleEnum.NONE.ordinal()
                && game.getBoard().getElement(0, 2) == game.getBoard().getElement(1, 1)
                && game.getBoard().getElement(1, 1) == game.getBoard().getElement(2, 0)))
            result = true;
        return result;
    }
    /**
     * Проверяет, допустим ли новый ход в игре.
     *
     * @param newStepGame новая игра с текущим ходом
     * @param oldGame старая игра для проверки состояния
     * @param user UUID текущего игрока
     * @return true, если ход допустим; иначе выбрасывает исключение
     */
    public static boolean validateGame(Game newStepGame, Game oldGame, UUID user) {
        if (oldGame == null || newStepGame == null) {
            throw new IllegalArgumentException("Игра не может быть null");
        }
        UUID oldCurPlayer = oldGame.getCurrentPlayer();
        if (!user.equals(oldCurPlayer)) {
            throw new SecurityException("Пользователь не является текущим игроком");
        }
        if (oldGame.isGameOver()) {
            throw new IllegalStateException("Игра уже завершена");
        }
        return checkField(newStepGame, oldGame, oldCurPlayer) == 1;
    }
    /**
     * Проверяет изменения на игровом поле.
     *
     * @param newStepGame новая игра с текущим ходом
     * @param oldGame старая игра для проверки состояния
     * @param oldCurPlayer UUID текущего игрока
     * @return количество изменений на поле
     */
    private static int checkField(Game newStepGame, Game oldGame, UUID oldCurPlayer) {
        int count = 0;
        final int currentTurn = oldCurPlayer.equals(newStepGame.getPlayer1()) ?
                RoleEnum.PLAYER1.ordinal() : RoleEnum.PLAYER2.ordinal();
        count = (int) IntStream.range(0, Consts.SIZE)
                .flatMap(i -> IntStream.range(0, Consts.SIZE)
                        .map(j -> {
                            return checkElem(newStepGame.getBoard().getElement(i, j),
                                    oldGame.getBoard().getElement(i, j),
                                    currentTurn);
                        }))
                .sum();
        return count;
    }
    /**
     * Проверяет отдельный элемент на поле.
     *
     * @param newStepGame новое значение элемента
     * @param oldGame старое значение элемента
     * @param currentTurn текущий игрок
     * @return результат проверки
     */
    private static int checkElem(int newStepGame, int oldGame, int currentTurn) {
        int res = 0;
        if (oldGame != 0 && newStepGame != oldGame)
            res = 2;
        else if (newStepGame != oldGame && newStepGame == currentTurn)
            res = 1;
        return res;
    }
}