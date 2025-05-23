package exercise.exercise.web.mapper;

import exercise.exercise.domain.model.User;
import exercise.exercise.web.model.SignUpRequest;
import exercise.exercise.web.model.UserWeb;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-23T19:50:47+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 18.0.2-ea (Private Build)"
)
public class UserMapperWebImpl implements UserMapperWeb {

    @Override
    public User fromWeb(SignUpRequest signUpRequest) {
        if ( signUpRequest == null ) {
            return null;
        }

        String username = null;
        String password = null;

        username = signUpRequest.getUsername();
        password = signUpRequest.getPassword();

        UUID id = java.util.UUID.randomUUID();

        User user = new User( id, username, password );

        return user;
    }

    @Override
    public UserWeb toWeb(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String username = null;

        id = user.getId();
        username = user.getUsername();

        UserWeb userWeb = new UserWeb( id, username );

        return userWeb;
    }
}
