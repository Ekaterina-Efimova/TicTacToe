package exercise.exercise.datasource.model;

import exercise.exercise.consts.Consts;
import exercise.exercise.domain.model.other.RoleEnum;

import lombok.Getter;

/**
 * Сущность, представляющая доску для игры в крестики-нолики.
 * Хранит состояние доски в виде двумерного массива целых чисел.
 */
@Getter
public class TicTacToeBoardEntity {
  private int[][] board = new int[Consts.SIZE][Consts.SIZE];
  /**
   * Конструктор, который инициализирует доску игры с заданным состоянием.
   * Проверяет корректность элементов доски с использованием RoleEnum.
   *
   * @param board двумерный массив, представляющий состояние доски
   * @throws IllegalArgumentException если элемент доски не является допустимым значением
   */
  public TicTacToeBoardEntity(int[][] board) {
    for (int i = 0; i < board.length; i++)
      for (int j = 0; j < board[i].length; j++) {
        if (!RoleEnum.checkRole(board[i][j]))
          throw new IllegalArgumentException("Invalid board element");
        this.board[i][j] = board[i][j];
      }
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
  /**
   * Получает количество столбцов в доске.
   *
   * @return количество столбцов
   */
  public int getCols() {
    return board.length;
  }
  /**
   * Получает количество строк в доске.
   *
   * @return количество строк
   */
  public int getRows() {
    return board[0].length;
  }
}