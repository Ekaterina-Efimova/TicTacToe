package exercise.exercise.di;

import exercise.exercise.web.filter.AuthFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности приложения.
 * Определяет настройки безопасности, включая авторизацию и фильтрацию запросов.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final DependencyGraphConfiguration context;
  /**
   * Настраивает цепочку фильтров безопасности.
   *
   * @param http объект HttpSecurity для настройки безопасности
   * @return настроенная цепочка фильтров безопасности
   * @throws Exception если произошла ошибка при настройке безопасности
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable) // Отключает CSRF-защиту
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**").permitAll() // Разрешает доступ к путям /auth/*
                    .anyRequest().authenticated()) // Все остальные запросы требуют аутентификации
            .addFilterBefore(new AuthFilter(context), UsernamePasswordAuthenticationFilter.class) // Добавляет фильтр аутентификации
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // Устанавливает политику управления сессиями
    return http.build(); // Возвращает настроенную цепочку фильтров
  }
}