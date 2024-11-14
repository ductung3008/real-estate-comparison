package com.example.realestate.security;
/*
 * @author HongAnh
 * @created 07 / 11 / 2024 - 10:58 CH
 * @project real-estate
 * @social Github: https://github.com/lehonganh0201
 * @social Facebook: https://www.facebook.com/profile.php?id=100047152174225
 */

import com.example.realestate.constant.Endpoint;
import com.example.realestate.constant.ErrorMessage;
import com.example.realestate.constant.Status;
import com.example.realestate.domain.dto.global.GlobalResponse;
import com.example.realestate.domain.dto.global.Meta;
import com.example.realestate.security.jwt.AuthTokenFilter;
import com.example.realestate.security.jwt.JwtAuthEntryPoint;
import com.example.realestate.util.MessageSourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    String CATCH_ALL_WILDCARD = "/**";

    JwtAuthEntryPoint      authEntryPoint;
    AuthTokenFilter        authTokenFilter;
    AuthenticationProvider authenticationProvider;
    ObjectMapper      objectMapper;
    MessageSourceUtil messageSourceUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(authEntryPoint);
                exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    GlobalResponse<Meta, Void> responseBody = GlobalResponse.<Meta, Void>builder()
                                                                            .meta(Meta.builder()
                                                                                      .status(Status.ERROR)
                                                                                      .message(messageSourceUtil.getLocalizedMessage(ErrorMessage.Auth.ERR_FORBIDDEN))
                                                                                      .build()
                                                                            )
                                                                            .build();
                    objectMapper.writeValue(response.getOutputStream(), responseBody);
                });
            })
            .authorizeHttpRequests(auth -> auth
//                    .anyRequest().permitAll()
                    .requestMatchers(Endpoint.V1.Auth.PREFIX + CATCH_ALL_WILDCARD).permitAll()
                    .requestMatchers(HttpMethod.GET, CATCH_ALL_WILDCARD).permitAll()
                    .requestMatchers(HttpMethod.POST, Endpoint.V1.User.PREFIX).permitAll()
                            .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider)
        .cors(Customizer.withDefaults());

        http.exceptionHandling().authenticationEntryPoint(authEntryPoint);
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
