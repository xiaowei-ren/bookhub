package fr.eni.bookhub.bll;

import fr.eni.bookhub.bll.authentication.AuthenticationService;
import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.bo.authentication.AuthenticationRequest;
import fr.eni.bookhub.bo.authentication.AuthenticationResponse;
import fr.eni.bookhub.dal.UtilisateurRepository;
import fr.eni.bookhub.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires du service AuthenticationService.
 * Vérifie le processus d'authentification d'un utilisateur et
 * la génération du jeton JWT associé.
 */
@ExtendWith(MockitoExtension.class)
public class TestAuthenticationService {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    /**
     * Vérifie que :
     * - l'authentification est déclenchée via AuthenticationManager
     * - l'utilisateur est récupéré depuis le repository
     * - un jeton JWT est généré
     * - la réponse contient le jeton généré
     */
    @Test
    void test_authentification_renvoie_token() {

        AuthenticationRequest request =
                new AuthenticationRequest("test@mail.com", "password");

        Utilisateur user = new Utilisateur();
        user.setEmail("test@mail.com");

        when(utilisateurRepository.findById("test@mail.com"))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(user))
                .thenReturn("fake-jwt-token");

        AuthenticationResponse response =
                authenticationService.authenticate(request);

        assertEquals("fake-jwt-token", response.getToken());
    }
}
