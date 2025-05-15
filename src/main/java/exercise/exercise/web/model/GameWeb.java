package exercise.exercise.web.model;

import exercise.exercise.consts.Consts;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * Веб-модель, представляющая игру в крестики-нолики.
 * Содержит информацию о состоянии игры, игроках и текущем ходе.
 */
@Component
@Getter
@Setter
public class GameWeb {

    /**
     * Уникальный идентификатор игры.
     */
    public UUID gameId;
    /**
     * Информация о правилах игры.
     */
    private String info = Consts.INFO;
    /**
     * Доска игры, представленная в виде двумерного массива целых чисел.
     */
    private int[][] board;
    /**
     * Флаг, указывающий, является ли игра одиночной (single player).
     */
    boolean singlePlayer;
    /**
     * Идентификатор первого игрока.
     */
    private UUID player1;
    /**
     * Идентификатор второго игрока.
     */
    private UUID player2;
    /**
     * Идентификатор текущего игрока, чей ход.
     */
    private UUID currentPlayer;
    /**
     * Заголовок авторизации.
     */
    private String authHeader;
    /**
     * Конструктор, инициализирующий доску игры с заданным размером.
     */
    public GameWeb() {
        this.board = new int[Consts.SIZE][Consts.SIZE];
    }
    /**
     * Получает элемент доски по заданным координатам.
     *
     * @param row номер строки
     * @param col номер столбца
     * @return значение элемента доски
     * @throws IndexOutOfBoundsException если указанные координаты выходят за пределы доски
     */
    public int getElement(int row, int col) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            throw new IndexOutOfBoundsException();
        }
        return board[row][col];
    }
    /**
     * Устанавливает значение элемента доски по заданным координатам.
     *
     * @param row номер строки
     * @param col номер столбца
     * @param value значение, которое нужно установить
     * @throws IndexOutOfBoundsException если указанные координаты выходят за пределы доски
     */
    public void setElement(int row, int col, int value) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
            throw new IndexOutOfBoundsException();
        }
        board[row][col] = value;
    }
}