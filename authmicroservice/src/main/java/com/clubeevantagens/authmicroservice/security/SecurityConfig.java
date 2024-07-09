package com.clubeevantagens.authmicroservice.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/password/forgot").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/password/reset").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/company/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/client/register").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/newtoken").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/users/client").hasAnyAuthority("SCOPE_CLIENT")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/client").hasAnyAuthority("SCOPE_CLIENT")
                        .requestMatchers(HttpMethod.PUT,"/api/users/company").hasAnyAuthority("SCOPE_COMPANY")
                        .requestMatchers(HttpMethod.DELETE,"/api/users/company").hasAnyAuthority("SCOPE_COMPANY")
                        .requestMatchers(HttpMethod.GET,"/api/users/client").hasAnyAuthority("SCOPE_CLIENT")
                        .requestMatchers(HttpMethod.GET,"/api/users/company").hasAnyAuthority("SCOPE_COMPANY")
                        .requestMatchers(HttpMethod.GET,"/api/users/company/all").hasAnyAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/users/company/all").hasAnyAuthority("SCOPE_ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(
                        conf -> conf.jwt(
                                jwt -> jwt.decoder(jwtDecoder())));;


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

}
