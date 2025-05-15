package exercise.exercise.consts;
/**
 * Класс, содержащий константы, используемые в приложении.
 * Включает размеры доски игры, сообщения и пути для работы с игровыми ресурсами.
 */
public class Consts {

    /**
     * Размер доски для игры в крестики-нолики.
     */
    public static final int SIZE = 3;
    /**
     * Информация о правилах игры.
     * X(1) - ход игрока 1, O(2) - ход игрока 2.
     */
    public static final String INFO = "X(1) - step player 1, O(2) - step player 2";
    /**
     * Путь для работы с игровыми ресурсами.
     */
    public static final String GAME_PATH = "/game";
    /**
     * Путь для создания новой игры.
     */
    public static final String GAME_NEW_PATH = "/new";
    /**
     * Путь для доступа к игре по ее уникальному идентификатору.
     */
    public static final String GAME_ID_PATH = "/{gameId}";
    /**
     * Путь для получения информации о пользователе по его UUID.
     */
    public static final String USER_INFO_PATH = "/user/{uuid}";
    /**
     * Путь для получения всех игр.
     */
    public static final String ALL_GAME_PATH = "/allGames";
    /**
     * Путь для получения доступных игр.
     */
    public static final String AVAILABLE_PATH = "/available";
    /**
     * Путь для аутентификации пользователей.
     */
    public static final String AUTH_PATH = "/auth";
    /**
     * Путь для регистрации новых пользователей.
     */
    public static final String REGISTER_PATH = "/register";
    /**
     * Путь для входа пользователей в систему.
     */
    public static final String LOGIN_PATH = "/login";
}