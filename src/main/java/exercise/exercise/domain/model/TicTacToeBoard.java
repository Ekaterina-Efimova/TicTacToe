package exercise.exercise.domain.model;

import exercise.exercise.consts.Consts;

import lombok.Getter;

/**
 * Класс, представляющий доску для игры в крестики-нолики.
 * Хранит состояние доски в виде двумерного массива целых чисел.
 */
@Getter
public class TicTacToeBoard {
  private int[][] board;
  /**
   * Конструктор, который инициализирует доску игры с заданным размером.
   */
  public TicTacToeBoard() {
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