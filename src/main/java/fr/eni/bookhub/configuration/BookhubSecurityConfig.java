package fr.eni.bookhub.configuration;

import fr.eni.bookhub.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration de la sécurité Spring Security pour l'API BookHub.
 *
 * Cette classe configure la SecurityFilterChain utilisée pour traiter
 * les requêtes HTTP entrantes. Elle définit :
 *
 * - les règles d'autorisation d'accès aux endpoints
 * - l'AuthenticationProvider utilisé pour l'authentification
 * - l'ajout du filtre JWT dans la chaîne de filtres
 * - une politique de session stateless adaptée aux API REST
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class BookhubSecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {auth
                /*
                 * Endpoints publics permettant l'inscription et l'authentification
                 */
                .requestMatchers("/bookhub/auth/**").permitAll()

                .anyRequest().authenticated();

        });

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.csrf(csrf -> {
            csrf.disable();
        });

        return http.build();
    }

}
