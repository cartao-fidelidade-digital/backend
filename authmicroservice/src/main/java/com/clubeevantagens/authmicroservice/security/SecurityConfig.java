package com.clubeevantagens.authmicroservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/password/forgot").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/password/reset").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/company/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/client/register").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/users/client/{id}").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/client/{id}").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.PUT,"/api/users/company/{id}").hasRole("COMPANY")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/company/{id}").hasRole("COMPANY")
                        .anyRequest().authenticated());


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
