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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .requestMatchers("/api/auth/**").permitAll()

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

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
