package exercise.exercise.datasource.mapper;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import exercise.exercise.domain.model.User;
import exercise.exercise.datasource.model.UserEntity;

/**
 * Интерфейс для преобразования между сущностями пользователя и их представлениями.
 * Использует MapStruct для автоматического создания реализаций мапперов.
 */
@Mapper
public interface UserMapperData {
  UserMapperData INSTANCE = Mappers.getMapper(UserMapperData.class);
  /**
   * Преобразует сущность пользователя (UserEntity) в объект пользователя (User).
   *
   * @param userEntity сущность пользователя для преобразования
   * @return объект пользователя
   */
  User fromData(UserEntity userEntity);
  /**
   * Преобразует объект пользователя (User) в сущность пользователя (UserEntity).
   *
   * @param user объект пользователя для преобразования
   * @return сущность пользователя
   */
  UserEntity toData(User user);
  /**
   * Преобразует объект Optional<UserEntity> в объект User.
   *
   * @param userEntity объект Optional, содержащий сущность пользователя
   * @return объект пользователя или null, если userEntity пустой
   */
  default User fromData(Optional<UserEntity> userEntity) {
    return userEntity.map(this::fromData).orElse(null);
  }
}
