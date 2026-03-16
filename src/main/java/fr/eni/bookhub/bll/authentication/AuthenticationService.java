package fr.eni.bookhub.bll.authentication;

import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.bo.authentication.AuthenticationRequest;
import fr.eni.bookhub.bo.authentication.AuthenticationResponse;
import fr.eni.bookhub.bo.authentication.AuthenticationResult;
import fr.eni.bookhub.dal.UtilisateurRepository;
import fr.eni.bookhub.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

/**
 * Service responsable de l'authentification des utilisateurs.
 *
 * Ce service vérifie les identifiants fournis via le AuthenticationManager.
 * Si l'authentification est réussie, l'utilisateur est récupéré depuis la base
 * de données et un jeton JWT est généré afin d'être utilisé pour les requêtes
 * authentifiées suivantes.
 */
@AllArgsConstructor
@Service
public class AuthenticationService {
    private UtilisateurRepository utilisateurRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;



    /**
     * Authentifie un utilisateur et génère un jeton JWT.
     *
     * Les identifiants fournis sont validés par le AuthenticationManager.
     * Si l'authentification réussit, un jeton JWT est généré pour l'utilisateur.
     *
     * @param request informations d'authentification (email et mot de passe)
     * @return le jeton JWT généré
     */
    public AuthenticationResult authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Utilisateur user = utilisateurRepository.findById(request.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        AuthenticationResult authenticationResult = AuthenticationResult.builder()
                .token(jwtToken)
                .prenom(user.getPrenom())
                .role(user.getRole())
                .build();

        return authenticationResult;
    }
}
