package exercise.exercise.web.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Модель пользователя для веб-приложения.
 * Содержит информацию о пользователе, включая его уникальный идентификатор и имя пользователя.
 */
@Getter
@Setter
@AllArgsConstructor
public class UserWeb {

    /**
     * Уникальный идентификатор пользователя.
     */
    private UUID id;
    /**
     * Имя пользователя, используемое для входа в систему.
     */
    private String username;
}