package com.example.doesnotexist.desk_bookings_server.config;

import com.example.doesnotexist.desk_bookings_server.services.MongoUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
@Slf4j
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityWebFilterChain(HttpSecurity http,
                                               MongoUserDetailsService mongoUserDetailsService
    ) throws Exception {
        return http.cors(Customizer.withDefaults())
                .authorizeHttpRequests(a -> {
                    a.requestMatchers("/").permitAll();
                    a.requestMatchers("/swagger-ui").permitAll();
                    a.requestMatchers("/swagger-ui/**").permitAll();
                    a.requestMatchers("/api-docs/**").permitAll();
                    a.anyRequest().authenticated();
                })
                .userDetailsService(mongoUserDetailsService)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
                .httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
