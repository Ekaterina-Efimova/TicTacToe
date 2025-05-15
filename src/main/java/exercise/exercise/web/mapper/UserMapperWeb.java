package exercise.exercise.web.mapper;

import exercise.exercise.domain.model.User;
import exercise.exercise.web.model.SignUpRequest;
import exercise.exercise.web.model.UserWeb;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.Optional;
/**
 * Интерфейс для преобразования между доменными моделями пользователя и веб-моделями.
 * Использует MapStruct для автоматического создания реализаций мапперов.
 */
@Mapper
public interface UserMapperWeb {
    UserMapperWeb INSTANCE = Mappers.getMapper(UserMapperWeb.class);
    /**
     * Преобразует запрос на регистрацию пользователя (SignUpRequest) в доменную модель пользователя (User).
     * Генерирует уникальный идентификатор для нового пользователя.
     *
     * @param signUpRequest объект запроса на регистрацию пользователя
     * @return доменная модель пользователя
     */
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    User fromWeb(SignUpRequest signUpRequest);
    /**
     * Преобразует доменную модель пользователя (User) в веб-модель пользователя (UserWeb).
     *
     * @param user доменная модель пользователя для преобразования
     * @return веб-модель пользователя
     */
    UserWeb toWeb(User user);
    /**
     * Преобразует объект Optional<User> в Optional<UserWeb>.
     *
     * @param user объект Optional, содержащий доменную модель пользователя
     * @return объект Optional, содержащий веб-модель пользователя
     */
    default Optional<UserWeb> toWeb(Optional<User> user) {
        return user.map(this::toWeb);
    }
}