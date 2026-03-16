package fr.eni.bookhub.controller.authentication;

import fr.eni.bookhub.bll.UtilisateurService;
import fr.eni.bookhub.bll.authentication.AuthenticationService;
import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.bo.UtilisateurReponse;
import fr.eni.bookhub.bo.authentication.AuthenticationRequest;
import fr.eni.bookhub.bo.authentication.AuthenticationResponse;

import fr.eni.bookhub.bo.authentication.AuthenticationResult;
import fr.eni.bookhub.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * Contrôleur REST d'authentification.
 *
 * Expose les endpoints publics liés à l'inscription, la modification des informations utilisateurs
 * et, à la connexion des utilisateurs de la plateforme BookHub.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private UtilisateurService utilisateurService;

    private final AuthenticationService authenticationService;

    /**
     * Inscrit un nouvel utilisateur sur la plateforme.
     *
     * Les données reçues sont validées avant traitement.
     * En cas de succès, l'utilisateur créé est renvoyé dans la réponse.
     *
     * @param utilisateur données d'inscription
     * @return utilisateur créé
     */
    @PostMapping("/register")
    public ResponseEntity<?> inscrireUtilisateur (@Valid @RequestBody Utilisateur utilisateur){
            UtilisateurReponse utilisateurBD = utilisateurService.ajouterUtilisateur(utilisateur);
            return ResponseEntity.ok(utilisateurBD);
    }

    /**
     * Authentifie un utilisateur et crée une session d'authentification basée sur JWT.
     *
     * Les identifiants fournis sont validés par le service d'authentification.
     * Si l'authentification réussit, un jeton JWT est généré puis placé dans un
     * cookie HTTP sécurisé (HttpOnly). Ce cookie sera automatiquement envoyé par
     * le navigateur lors des requêtes suivantes afin d'authentifier l'utilisateur.
     *
     * Le jeton n'est pas renvoyé dans le corps de la réponse afin de limiter son
     * exposition côté client.
     *
     * @param request  informations d'authentification (email et mot de passe)
     * @param response réponse HTTP utilisée pour ajouter le cookie contenant le JWT
     * @return réponse HTTP 200 si l'authentification est réussie
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentifierUtilisateur(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response) {

        AuthenticationResult authenticationResult = authenticationService.authenticate(request);

        ResponseCookie cookie = ResponseCookie.from(("access_token"), authenticationResult.getToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtService.getExpirationSeconds())
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthenticationResponse body = AuthenticationResponse.builder()
                .prenom(authenticationResult.getPrenom())
                .role(authenticationResult.getRole())
                .build();

        return ResponseEntity.ok().body(body);
    }

}
