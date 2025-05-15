package exercise.exercise.datasource.service;

import exercise.exercise.datasource.model.UserEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для управления сущностями пользователей.
 * Обеспечивает доступ к данным пользователей через интерфейс UserCrudRepository
 * и выполняет операции, такие как получение и сохранение пользователей.
 */
@Repository
public class UserRepository {
    private final UserCrudRepository userCrudRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * Конструктор, инициализирующий репозиторий с использованием UserCrudRepository
     * и устанавливающий шифровщик паролей.
     *
     * @param userCrudRepository интерфейс для работы с CRUD-операциями над сущностями пользователей
     */
    public UserRepository(UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    /**
     * Получает пользователя по имени пользователя (логину).
     *
     * @param username имя пользователя для поиска
     * @return объект Optional, содержащий сущность пользователя, если она найдена, иначе пустой объект
     */
    public Optional<UserEntity> getUserByLogin(String username) {
        List<UserEntity> users = userCrudRepository.getUserEntityByUsername(username);
        return users.isEmpty() ? Optional.empty() : Optional.ofNullable(users.get(0));
    }
    /**
     * Получает пользователя по уникальному идентификатору.
     *
     * @param id уникальный идентификатор пользователя
     * @return объект Optional, содержащий сущность пользователя, если она найдена, иначе пустой объект
     */
    public Optional<UserEntity> getUserByUUID(UUID id) {
        return userCrudRepository.getUserById(id);
    }
    /**
     * Сохраняет сущность пользователя в репозитории.
     * Пароль пользователя шифруется перед сохранением.
     *
     * @param user сущность пользователя для сохранения
     */
    public void saveUser(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userCrudRepository.save(user);
    }
}