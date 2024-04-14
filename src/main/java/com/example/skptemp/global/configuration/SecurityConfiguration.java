package com.example.skptemp.global.configuration;

import com.example.skptemp.domain.user.service.UserService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final String[] permittedPatterns = {
            "/api/v1/users/login",
            "/api/v1/users/sign-up",
            "/api/v1/users/create-token-100days",
            "/api/v1/users/create-token-10min",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-config" };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers(permittedPatterns).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtFilter(jwtProvider, userService), BasicAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
