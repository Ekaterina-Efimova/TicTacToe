package exercise.exercise.domain.model;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
/**
 * Модель игры в крестики-нолики.
 * Содержит информацию о состоянии игры, игроках и текущем ходе.
 */
@Getter
@Setter
public class Game {
    private UUID gameId; // Уникальный идентификатор игры
    private TicTacToeBoard board; // Доска игры
    private String move; // Текущий ход
    private String authHeader; // Заголовок авторизации
    private boolean gameOver; // Флаг, указывающий, завершена ли игра
    private boolean singlePlayer; // Флаг, указывающий, является ли игра одиночной
    private GameStateEnum state; // Состояние игры
    private UUID player1; // Идентификатор первого игрока
    private UUID player2; // Идентификатор второго игрока
    private UUID currentPlayer; // Идентификатор текущего игрока
    private UUID winner; // Идентификатор победителя

    /**
     * Конструктор по умолчанию.
     */
    public Game() {
    }
    /**
     * Конструктор, инициализирующий игру с заданным уникальным идентификатором.
     *
     * @param gameId уникальный идентификатор игры
     */
    public Game(UUID gameId) {
        this.gameId = gameId;
        this.board = new TicTacToeBoard(); // Инициализация новой доски игры
        this.gameOver = false; // Игра еще не завершена
        this.state = GameStateEnum.NONE; // Начальное состояние игры
    }
    /**
     * Конструктор, инициализирующий игру с заданным уникальным идентификатором и доской.
     *
     * @param gameId уникальный идентификатор игры
     * @param board  доска игры
     * @param move   текущий ход
     */
    public Game(UUID gameId, TicTacToeBoard board, String move) {
        this.gameId = gameId;
        this.board = board;
        this.move = move;
    }
    /**
     * Устанавливает текущий ход.
     *
     * @param move текущий ход
     */
    public void setMove(String move) {
        this.move = move;
    }
}