package exercise.exercise.datasource.service;

import exercise.exercise.datasource.model.GameEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления сущностями игры.
 * Предоставляет стандартные операции CRUD для сущностей GameEntity.
 */
@Repository
public interface GameCrudRepository extends CrudRepository<GameEntity, String> {}
