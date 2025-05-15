package exercise.exercise.domain.service;

import exercise.exercise.domain.model.User;

import java.util.Optional;
import java.util.UUID;
/**
 * Интерфейс для управления пользователями в системе.
 * Предоставляет методы для регистрации, получения и аутентификации пользователей.
 */
public interface UserService {
    /**
     * Регистрирует нового пользователя.
     *
     * @param user объект пользователя, который необходимо зарегистрировать
     * @return true, если регистрация прошла успешно, иначе false
     */
    boolean register(User user);
    /**
     * Получает пользователя по его уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор пользователя
     * @return объект Optional, содержащий пользователя, если он найден, иначе пустой объект
     */
    Optional<User> getUserByUUID(UUID uuid);
    /**
     * Получает пользователя по его логину.
     *
     * @param login логин пользователя для поиска
     * @return объект Optional, содержащий пользователя, если он найден, иначе пустой объект
     */
    Optional<User> getUserByLogin(String login);
    /**
     * Авторизует пользователя на основе заголовка авторизации.
     *
     * @param authHeader заголовок авторизации
     * @return уникальный идентификатор пользователя, если авторизация прошла успешно
     */
    UUID authorize(String authHeader);
    /**
     * Аутентифицирует пользователя на основе логина и пароля.
     *
     * @param login логин пользователя
     * @param password пароль пользователя
     * @return объект Optional, содержащий пользователя, если аутентификация прошла успешно, иначе пустой объект
     */
    Optional<User> authenticate(String login, String password);
}