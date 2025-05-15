package exercise.exercise.web.controller;

import exercise.exercise.consts.Consts;
import exercise.exercise.di.DependencyGraphConfiguration;
import exercise.exercise.web.mapper.UserMapperWeb;
import exercise.exercise.web.model.SignUpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
/**
 * Контроллер для управления пользователями.
 * Обрабатывает HTTP-запросы, связанные с регистрацией и входом пользователей.
 */
@Controller
@RequestMapping(Consts.AUTH_PATH)
public class UserController {
    private final DependencyGraphConfiguration context;
    /**
     * Конструктор, инициализирующий контроллер с контекстом зависимостей.
     *
     * @param context контекст зависимостей для доступа к сервисам пользователей
     */
    @Autowired
    public UserController(DependencyGraphConfiguration context) {
        this.context = context;
    }
    /**
     * Регистрация нового пользователя.
     *
     * @param request объект SignUpRequest, содержащий данные для регистрации
     * @return ResponseEntity с статусом 200 (OK) при успешной регистрации или 400 (Bad Request) в случае ошибки
     */
    @PostMapping(Consts.REGISTER_PATH)
    public ResponseEntity<Void> register(@RequestBody SignUpRequest request) {
        return context.userService().register(UserMapperWeb.INSTANCE.fromWeb(request))
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
    /**
     * Вход пользователя в систему.
     *
     * @param authHeader заголовок авторизации, содержащий учетные данные пользователя
     * @return ResponseEntity с UUID пользователя при успешном входе или статусом 401 (Unauthorized) в случае ошибки безопасности
     */
    @PostMapping(Consts.LOGIN_PATH)
    public ResponseEntity<UUID> login(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(context.userService().authorize(authHeader));
        } catch (SecurityException e) {
            return ResponseEntity.status(401).build();
        }
    }
}