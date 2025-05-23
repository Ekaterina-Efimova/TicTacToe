package exercise.exercise.web.filter;

import exercise.exercise.di.DependencyGraphConfiguration;
import exercise.exercise.web.mapper.UserMapperWeb;
import exercise.exercise.web.model.UserWeb;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

/**
 * Фильтр аутентификации, который проверяет учетные данные пользователей
 * из заголовка Authorization и устанавливает контекст безопасности.
 */
public class AuthFilter extends GenericFilterBean {
    private final DependencyGraphConfiguration context;

    /**
     * Конструктор, инициализирующий фильтр с контекстом зависимостей.
     *
     * @param context контекст зависимостей для доступа к сервисам пользователей
     */
    public AuthFilter(DependencyGraphConfiguration context) {
        this.context = context;
    }

    /**
     * Метод фильтрации, который проверяет учетные данные пользователей.
     * Если путь запроса соответствует аутентификации, пропускает запрос дальше.
     * В противном случае выполняет аутентификацию пользователя.
     *
     * @param req   объект запроса
     * @param res   объект ответа
     * @param chain цепочка фильтров
     * @throws IOException      если произошла ошибка ввода-вывода
     * @throws ServletException если произошла ошибка сервлета
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI();

        if (path.startsWith("/auth/register") || path.startsWith("/auth/login"))
            chain.doFilter(req, res);

        else {
            try {
                String[] creds = extractCredentials(request.getHeader("Authorization"));
                UserWeb user = UserMapperWeb.INSTANCE.toWeb(context.userService().authenticate(creds[0], creds[1]))
                        .orElseThrow(() -> new SecurityException("Unauthorized"));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                        Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(req, res);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    /**
     * Извлекает учетные данные из заголовка Authorization.
     *
     * @param header заголовок Authorization
     * @return массив строк, содержащий логин и пароль
     * @throws IllegalArgumentException если заголовок недействителен или формат
     *                                  учетных данных неверен
     */
    private String[] extractCredentials(String header) {
        if (header == null || !header.startsWith("Basic "))
            throw new IllegalArgumentException("Invalid Authorization header");

        String base64Credentials = header.substring("Basic ".length()).trim();
        
        byte[] decodedBytes;
        try {
            decodedBytes = Base64.getDecoder().decode(base64Credentials);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to decode Base64 credentials", e);
        }

        String credentials = new String(decodedBytes, StandardCharsets.UTF_8);
        int colonIndex = credentials.indexOf(':');
        if (colonIndex == -1)
            throw new IllegalArgumentException("Invalid credentials format");

        String login = credentials.substring(0, colonIndex);
        String password = credentials.substring(colonIndex + 1);
        return new String[] { login, password };
    }
}