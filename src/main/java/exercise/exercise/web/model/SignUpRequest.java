package exercise.exercise.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель запроса на регистрацию пользователя.
 * Содержит информацию, необходимую для создания нового пользователя.
 */
@Getter
@Setter
public class SignUpRequest {

    /**
     * Имя пользователя, которое будет использоваться для входа в систему.
     */
    private String username;
    /**
     * Пароль пользователя, используемый для аутентификации.
     */
    private String password;
}