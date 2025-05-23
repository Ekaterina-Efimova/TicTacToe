package exercise.exercise.domain.service;

import exercise.exercise.datasource.mapper.UserMapperData;
import exercise.exercise.datasource.service.UserRepository;
import exercise.exercise.domain.model.User;

import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса для управления пользователями.
 * Предоставляет методы для регистрации, аутентификации и получения
 * пользователей.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Конструктор, инициализирующий сервис с использованием UserRepository.
     *
     * @param userRepository репозиторий для работы с сущностями пользователей
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param user объект пользователя для регистрации
     * @return true, если регистрация прошла успешно, иначе false
     */
    public boolean register(User user) {
        boolean result = false;
        if (getUserByLogin(user.getUsername()).isEmpty()) {
            result = true;
            userRepository.saveUser(UserMapperData.INSTANCE.toData(user));
        }
        return result;
    }

    /**
     * Аутентифицирует пользователя по логину и паролю.
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return объект Optional, содержащий пользователя, если аутентификация прошла
     *         успешно, иначе пустой объект
     */
    public Optional<User> authenticate(String login, String password) {
        Optional<User> user = getUserByLogin(login);
        if (user.isEmpty() || !userRepository.getPasswordEncoder().matches(password, user.get().getPassword()))
            throw new SecurityException("Invalid credentials");
        return user;
    }

    /**
     * Получает пользователя по уникальному идентификатору.
     *
     * @param uuid уникальный идентификатор пользователя
     * @return объект Optional, содержащий пользователя, если он найден, иначе
     *         пустой объект
     */
    public Optional<User> getUserByUUID(UUID uuid) {
        return Optional.of(UserMapperData.INSTANCE.fromData(userRepository.getUserByUUID(uuid)));
    }

    /**
     * Получает пользователя по логину.
     *
     * @param login логин пользователя
     * @return объект Optional, содержащий пользователя, если он найден, иначе
     *         пустой объект
     */
    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.getUserByLogin(login).map(UserMapperData.INSTANCE::fromData);
    }

    /**
     * Авторизует пользователя на основе заголовка авторизации.
     *
     * @param authHeader заголовок авторизации
     * @return уникальный идентификатор пользователя
     * @throws SecurityException если учетные данные неверны
     */
    @Override
    public UUID authorize(String authHeader) {
        String[] creds = extractCredentials(authHeader);
        return authenticate(creds[0], creds[1])
                .orElseThrow(() -> new SecurityException("Invalid credentials"))
                .getId();
    }

    /**
     * Извлекает учетные данные из заголовка авторизации.
     *
     * @param header заголовок авторизации
     * @return массив строк с логином и паролем
     * @throws IllegalArgumentException если заголовок неверен
     */
    private String[] extractCredentials(String header) {
        if (header == null || !header.startsWith("Basic "))
            throw new IllegalArgumentException("Invalid Authorization header");
        String credentials = new String(Base64.getDecoder().decode(header.substring("Basic ".length())),
                StandardCharsets.UTF_8);
        int colonIndex = credentials.indexOf(':');
        if (colonIndex == -1)
            throw new IllegalArgumentException("Invalid credentials format");
        return new String[] { credentials.substring(0, colonIndex),
                credentials.substring(colonIndex + 1) };
    }
}