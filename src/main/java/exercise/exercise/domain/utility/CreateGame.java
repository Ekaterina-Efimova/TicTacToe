package exercise.exercise.domain.utility;

import exercise.exercise.consts.Consts;
import exercise.exercise.domain.model.other.RoleEnum;
import exercise.exercise.domain.model.Game;

import java.util.Random;
import java.util.UUID;
/**
 * Утилитный класс для создания игры.
 * Обеспечивает методы для инициализации игры в зависимости от типа: одиночная или многопользовательская.
 */
public class CreateGame {
    /**
     * Создает новую игру на основе предоставленных параметров.
     *
     * @param userGame объект игры, содержащий данные для новой игры
     * @param oldGame существующая игра, если она есть
     * @param user уникальный идентификатор пользователя, создающего игру
     * @return объект Game, представляющий созданную или обновленную игру
     */
    public static Game createGame(Game userGame, Game oldGame, UUID user) {
        if (oldGame == null) {
            Random random = new Random();
            oldGame = new Game(userGame.getGameId());
            if (userGame.isSinglePlayer())
                createGameSingle(oldGame, user, random);
            else
                createGameMulti(oldGame, user, random.nextBoolean());
        }
        return oldGame;
    }
    /**
     * Создает одиночную игру.
     *
     * @param oldGame объект игры, который нужно инициализировать
     * @param user уникальный идентификатор пользователя
     * @param random генератор случайных чисел для определения начального хода
     */
    private static void createGameSingle(Game oldGame, UUID user, Random random) {
        if (random.nextBoolean()) {
            setPlayer(oldGame, UUID.fromString("00000000-0000-0000-0000-000000000000"),
                    UUID.fromString("00000000-0000-0000-0000-000000000000"), user);
            oldGame.getBoard().setElement(random.nextInt(Consts.SIZE), random.nextInt(Consts.SIZE), RoleEnum.PLAYER1.ordinal());
            oldGame.setCurrentPlayer(oldGame.getCurrentPlayer().equals(oldGame.getPlayer1())
                    ? oldGame.getPlayer2()
                    : oldGame.getPlayer1());
        } else {
            setPlayer(oldGame, user, user, UUID.fromString("00000000-0000-0000-0000-000000000000"));
        }
        oldGame.setSinglePlayer(true);
    }
    /**
     * Создает многопользовательскую игру.
     *
     * @param oldGame объект игры, который нужно инициализировать
     * @param user уникальный идентификатор пользователя
     * @param random флаг, указывающий, должен ли игрок быть случайно выбранным
     */
    private static void createGameMulti(Game oldGame, UUID user, boolean random) {
        if (random)
            setPlayer(oldGame, user, user, null);
        else
            oldGame.setPlayer2(user);
    }
    /**
     * Устанавливает игроков в игре.
     *
     * @param oldGame объект игры, в который нужно установить игроков
     * @param player1 уникальный идентификатор первого игрока
     * @param currentPlayer уникальный идентификатор текущего игрока
     * @param player2 уникальный идентификатор второго игрока (может быть null)
     */
    private static void setPlayer(Game oldGame, UUID player1, UUID currentPlayer, UUID player2) {
        oldGame.setPlayer1(player1);
        if (player2 != null) oldGame.setPlayer2(player2);
        oldGame.setCurrentPlayer(currentPlayer);
    }
}