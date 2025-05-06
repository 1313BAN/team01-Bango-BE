package com.ssafy.ssafyhome.global.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberDetailsService memberDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable) // API 서버인 경우 토큰 기반 CSRF, 필요 시 켜기
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/public/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginProcessingUrl("/api/auth/login")
                        .usernameParameter("id")
                        .successHandler((req, res, auth) -> {
                            log.warn("success!! + {}", auth.getPrincipal());
                            // 세션 쿠키는 이미 Set-Cookie 헤더로 내려가 있습니다.
                            res.setStatus(HttpServletResponse.SC_OK);
                            res.getWriter().write("{\"status\":\"ok\"}");
                        })
                        .failureHandler((req, res, ex) -> {
                            log.warn("FAILURE!! + {}", ex.getMessage());
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );
        return http.build();
    }

    // AuthenticationProvider 설정: UserDetailsService + PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authProvider() {

        DaoAuthenticationProvider p = new DaoAuthenticationProvider();

        p.setUserDetailsService(memberDetailsService);
        p.setPasswordEncoder(passwordEncoder());

        return p;
    }

    /**
     * WebMvcConfigurer 대신 Security 앞단에서 CORS를 처리할 CorsConfigurationSource
     * Security는 Spring MVC 이전에 Filter에서 동작하기 때문에 따로 처리해주어야 함.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173"        // 프론트엔드 Vite 서버
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);  // ★ 반드시 true로

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

