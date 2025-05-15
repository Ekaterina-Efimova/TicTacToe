package exercise.exercise.datasource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Сущность, представляющая пользователя в базе данных.
 * Хранит информацию о пользователе, включая уникальный идентификатор, имя пользователя и пароль.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    private UUID id;
    /**
     * Имя пользователя, должно быть уникальным и не может быть пустым.
     */
    @Column(unique = true, nullable = false)
    private String username;
    /**
     * Пароль пользователя, не может быть пустым.
     */
    @Column(nullable = false)
    private String password;
}