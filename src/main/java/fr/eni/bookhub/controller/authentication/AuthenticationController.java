package fr.eni.bookhub.controller.authentication;

import fr.eni.bookhub.bll.UtilisateurService;
import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.exceptions.BusinessException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("bookhub/auth")
public class AuthenticationController {

    private UtilisateurService utilisateurService;
    private MessageSource messageSource;

    @PostMapping("/register")
    public ResponseEntity<?> inscrireUtilisateur (@Valid @RequestBody Utilisateur utilisateur){
        try {
            Utilisateur utilisateurBD = utilisateurService.ajouterUtilisateur(utilisateur);
            return ResponseEntity.ok(utilisateurBD);
        } catch (BusinessException e){
            List<String> messages = e.getClefsExternalisations().stream()
                    .map(clef -> messageSource.getMessage(clef, null, Locale.getDefault())).toList();

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messages);
        }
    }

}
