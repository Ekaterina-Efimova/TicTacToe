package exercise.exercise.datasource.mapper;

import exercise.exercise.datasource.model.UserEntity;
import exercise.exercise.domain.model.User;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-23T19:50:47+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 18.0.2-ea (Private Build)"
)
public class UserMapperDataImpl implements UserMapperData {

    @Override
    public User fromData(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String password = null;

        id = userEntity.getId();
        username = userEntity.getUsername();
        password = userEntity.getPassword();

        User user = new User( id, username, password );

        return user;
    }

    @Override
    public UserEntity toData(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setUsername( user.getUsername() );
        userEntity.setPassword( user.getPassword() );

        return userEntity;
    }
}
