package fr.eni.bookhub.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Cet objet permet de renvoyer les informations d'un utilisateur
 * en réponse aux requêtes de l'API, sans exposer les informations sensibles
 * telles que le mot de passe.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = {"email"})
public class UtilisateurReponse {

    private String email;

    private String nom;

    private String prenom;

}
