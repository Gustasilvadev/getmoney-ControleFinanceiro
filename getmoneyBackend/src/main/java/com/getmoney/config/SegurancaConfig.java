package com.getmoney.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {

    @Autowired
    SecurityFilter securityFilter;

    public static final String[] SWAGGER_ENDPOINTS = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/test-expo-url"
    };

    public static final String[] ENDPOINTS_SEM_AUTENTICACAO = {
            "/api/autenticacao/registrarUsuario",
            "/api/autenticacao/autenticarUsuario",
            "/test-expo-url",

            // Páginas web
                "/",
                "/install",
                "/index.html",
                "/install.html",
                "video.html",

                // Download do APK (endpoint e arquivo)
                "/download-apk",
                "/app-release.apk",

                // Recursos estáticos: imagens
                 "/images/**",
                "/images/*",
                "/images",
    };

    public static final String[] ENDPOINTS_WEB_PAGES = {
            "/",
            "/install",
            "/download-apk",
            "/download/app"
    };

    /**
     * Cria e configura um bean do tipo PasswordEncoder para criptografia de senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Sistema de segurança da aplicação Spring Boot
     * Definição das permissões dos endpoints
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity

                .csrf(csrf -> csrf.disable())
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authorizeHttpRequests(authorize -> authorize
                       .requestMatchers(ENDPOINTS_SEM_AUTENTICACAO).permitAll()
                       .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                       .requestMatchers(ENDPOINTS_WEB_PAGES).permitAll()
                       .anyRequest().authenticated()
               )
               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    /**
     * Classe de configuração do Spring Security
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
