package fr.eni.bookhub.controller.authentication;

import fr.eni.bookhub.bll.UtilisateurService;
import fr.eni.bookhub.bll.authentication.AuthenticationService;
import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.bo.UtilisateurReponse;
import fr.eni.bookhub.bo.authentication.AuthenticationRequest;
import fr.eni.bookhub.bo.authentication.AuthenticationResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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
@RequestMapping("bookhub/auth")
public class AuthenticationController {

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
     * Authentifie un utilisateur et génère un jeton JWT.
     *
     * Si les identifiants fournis sont valides, un jeton d'authentification
     * est généré et renvoyé dans la réponse.
     *
     * @param request informations d'authentification
     * @return réponse contenant le jeton JWT
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentifierUtilisateur(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
