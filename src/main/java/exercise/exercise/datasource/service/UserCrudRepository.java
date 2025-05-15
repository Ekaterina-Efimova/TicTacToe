package exercise.exercise.datasource.service;

import exercise.exercise.datasource.model.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Репозиторий для управления сущностями пользователей.
 * Предоставляет стандартные операции CRUD для сущностей UserEntity,
 * а также дополнительные методы для поиска пользователей.
 */
@Repository
public interface UserCrudRepository extends CrudRepository<UserEntity, String> {
    /**
     * Получает список сущностей пользователей по имени пользователя.
     *
     * @param username имя пользователя для поиска
     * @return список сущностей пользователей с указанным именем
     */
    List<UserEntity> getUserEntityByUsername(String username);
    /**
     * Получает сущность пользователя по уникальному идентификатору.
     *
     * @param id уникальный идентификатор пользователя
     * @return объект Optional, содержащий сущность пользователя, если она найдена, иначе пустой объект
     */
    Optional<UserEntity> getUserById(UUID id);
}