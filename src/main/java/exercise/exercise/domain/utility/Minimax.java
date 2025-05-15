package exercise.exercise.domain.utility;
/**
 * Класс, реализующий алгоритм минимакс для игры в крестики-нолики.
 * Алгоритм используется для нахождения оптимального хода для компьютера.
 */
public class Minimax {

  /**
   * Внутренний класс, представляющий оценку хода.
   */
  private class Score {
    private final int[] pos; // Позиция на доске
    private final int scorePoints; // Оценка хода
    Score(int[] pos, int scorePoints) {
      this.pos = new int[2];
      this.pos[0] = pos[0];
      this.pos[1] = pos[1];
      this.scorePoints = scorePoints;
    }
  }
  // Возможные выигрышные линии на доске
  private static int[][][] lines = new int[][][]{
          { { 0, 0 }, { 0, 1 }, { 0, 2 } },
          { { 1, 0 }, { 1, 1 }, { 1, 2 } },
          { { 2, 0 }, { 2, 1 }, { 2, 2 } },
          { { 0, 0 }, { 1, 0 }, { 2, 0 } },
          { { 0, 1 }, { 1, 1 }, { 2, 1 } },
          { { 0, 2 }, { 1, 2 }, { 2, 2 } },
          { { 0, 0 }, { 1, 1 }, { 2, 2 } },
          { { 0, 2 }, { 1, 1 }, { 2, 0 } }
  };
  private int comp = 2; // Идентификатор компьютера
  private int human = 1; // Идентификатор человека
  private static final int zero = 0; // Значение для пустой клетки
  /**
   * Находит оптимальный ход для компьютера.
   *
   * @param board текущая игровая доска
   * @return массив, представляющий позицию (строка, столбец) оптимального хода
   */
  public int[] findOptimalMovement(int[][] board) {
    Score score = miniMax(board, comp, 8);
    return score.pos;
  }
  /**
   * Рекурсивный метод минимакс для оценки всех возможных ходов.
   *
   * @param board текущая игровая доска
   * @param player текущий игрок (компьютер или человек)
   * @param depth глубина рекурсии
   * @return объект Score, содержащий лучшую позицию и оценку
   */
  private Score miniMax(int[][] board, int player, int depth) {
    int bestScore = (player == comp) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    int[] bestPos = { -1, -1 };
    if (depth == 0 || getGameStatus(board) != -1) {
      bestScore = evaluate(board);
    } else {
      int[][] tempBoard = copyBoard(board);
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (tempBoard[i][j] == zero) {
            tempBoard[i][j] = player;
            int currentScore = miniMax(tempBoard, player == comp ? human : comp, depth - 1).scorePoints;
            if (player == comp ? currentScore > bestScore : currentScore < bestScore) {
              bestScore = currentScore;
              bestPos[0] = i;
              bestPos[1] = j;
            }
            tempBoard[i][j] = zero;
          }
        }
      }
    }
    return new Score(bestPos, bestScore);
  }
  /**
   * Оценивает текущую игровую доску.
   *
   * @param board текущая игровая доска
   * @return оценка доски
   */
  private int evaluate(int[][] board) {
    int score = 0;
    for (int i = 0; i < lines.length; i++) {
      score += evaluateLine(board, lines[i]);
    }
    return score;
  }
  /**
   * Оценивает линию на доске.
   *
   * @param board текущая игровая доска
   * @param line линия для оценки
   * @return оценка линии
   */
  private int evaluateLine(int[][] board, int[][] line) {
    int score = 0;
    int pos1 = board[line[0][0]][line[0][1]];
    int pos2 = board[line[1][0]][line[1][1]];
    int pos3 = board[line[2][0]][line[2][1]];
    if (pos1 == comp) {
      score = 1;
    } else if (pos1 == human) {
      score = -1;
    }
    if (pos2 == comp) {
      if (score == 1) {
        score = 10;
      } else if (score == -1) {
        return 0;
      }
    } else if (pos2 == human) {
      if (score == -1) {
        score = -10;
      } else if (score == 1) {
        return 0;
      } else {
        score = -1;
      }
    }
    if (pos3 == comp) {
      if (score > 0) {
        score *= 10;
      } else if (score < 0) {
        return 0;
      }
    } else if (pos3 == human) {
      if (score < 0) {
        score *= 10;
      } else if (score > 0) {
        return 0;
      } else {
        score = -1;
      }
    }
    return score;
  }
  /**
   * Копирует игровую доску.
   *
   * @param originalBoard исходная доска
   * @return скопированная доска
   */
  private int[][] copyBoard(int[][] originalBoard) {
    int[][] copiedBoard = new int[3][3];
    for (int i = 0; i < 3; i++) {
      System.arraycopy(originalBoard[i], 0, copiedBoard[i], 0, 3);
    }
    return copiedBoard;
  }
  /**
   * Проверяет статус игры.
   *
   * @param tempBoard игровая доска для проверки
   * @return 1 для победы человека, 2 для победы компьютера, 0 для ничьей, -1 для продолжающейся игры
   */
  private int getGameStatus(int[][] tempBoard) {
    for (int[][] line : lines) {
      int val1 = tempBoard[line[0][0]][line[0][1]];
      int val2 = tempBoard[line[1][0]][line[1][1]];
      int val3 = tempBoard[line[2][0]][line[2][1]];
      if (val1 == 2 && val2 == 2 && val3 == 2) {
        return comp;
      } else if (val1 == 1 && val2 == 1 && val3 == 1) {
        return human;
      }
    }
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (tempBoard[i][j] == 0) {
          return -1;
        }
      }
    }
    return zero;
  }
}