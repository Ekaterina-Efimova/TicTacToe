package exercise.exercise.consts;

/**
 * Перечисление, представляющее роли игроков в игре.
 * Включает роли: NONE (нет роли), PLAYER1 (игрок 1) и PLAYER2 (игрок 2).
 */
public enum RoleEnum {
  NONE,
  PLAYER1,
  PLAYER2;
  /**
   * Возвращает строковое представление роли.
   *
   * @return имя роли в виде строки
   */
  public String getRole() {
    return this.name();
  }
  /**
   * Проверяет, является ли переданное значение допустимой ролью.
   *
   * @param value значение, представляющее роль
   * @return true, если значение соответствует допустимой роли; иначе false
   */
  public static Boolean checkRole(int value) {
    for (RoleEnum r : RoleEnum.values()) {
      if (r.ordinal() == value) {
        return true;
      }
    }
    return false;
  }
}