package exercise.exercise.domain.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс, представляющий пользователя в системе.
 * Хранит информацию о пользователе, включая уникальный идентификатор, имя пользователя и пароль.
 */
@Getter
@Setter
@AllArgsConstructor
public class User {
    /**
     * Уникальный идентификатор пользователя.
     */
    private UUID id;
    /**
     * Имя пользователя, используемое для аутентификации.
     */
    private String username;
    /**
     * Пароль пользователя, используемый для аутентификации.
     */
    private String password;
}