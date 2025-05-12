package com.ssafy.bango.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(AbstractHttpConfigurer::disable)
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
        .authorizeHttpRequests(auth -> auth
//            .requestMatchers("/api/auth/**", "/api/v1/dongcode/**", "/swagger-ui/**").permitAll()
//            .requestMatchers("/h2-console/**").permitAll()
//            .anyRequest().authenticated()
                .anyRequest().permitAll()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).build();
  }

//    // AuthenticationProvider 설정: UserDetailsService + PasswordEncoder
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//
//        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
//
//        p.setUserDetailsService(memberDetailsService);
//        p.setPasswordEncoder(passwordEncoder());
//
//        return p;
//    }

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

