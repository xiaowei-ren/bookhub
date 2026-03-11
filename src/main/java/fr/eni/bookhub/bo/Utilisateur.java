package fr.eni.bookhub.bo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * Représente un utilisateur de la plateforme BookHub.
 */
@NoArgsConstructor @AllArgsConstructor @Data
@EqualsAndHashCode(of = {"email"})
@Builder
@Entity
@Table( name = "BOOKHUB_USER")
public class Utilisateur {

    @Id
    @NotBlank(message = "{utilisateur.email.blank-error}")
    @Email(message = "{utilisateur.email.format-error}")
    @Column( nullable = false, length = 250)
    private String email;

    @NotBlank(message = "{utilisateur.nom.blank-error}")
    @Size(max = 150, message = "{utilisateur.nom.size-error}")
    @Column ( name = "LAST_NAME", nullable = false, length = 150)
    private String nom;

    @NotBlank(message = "{utilisateur.prenom.blank-error}")
    @Size(max = 150, message = "{utilisateur.prenom.size-error}")
    @Column(name = "FIRST_NAME", nullable = false, length = 150)
    private String prenom;

    /**
     * Le mot de passe doit être fortement sécurisé :
     * 1 majuscule au minimum
     * 1 minuscule au minimum
     * 1 chiffre au minimum
     * 1 caractère spécial au minimum
     * 8 caractères minimum
     */
    @NotBlank(message = "{utilisateur.password.blank-error}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$", message = "{utilisateur.password.regex-error}")
    @Size(max = 255, message = "{utilisateur.password.size-error}")
    @ToString.Exclude
    @Column (nullable = false, length = 255)
    private String password;

    /**
     * Représente le rôle attribué à l'utilisateur de la plateforme BookHub.
     * Un(e) Lecteur-ice peut consulter le catalogue de la bibliothèque, emprunter et réserver des livres,
     * noter et écrire des avis sur les livres empruntés et consulter ses emprunts présents et passés.
     *
     * Un(e) Bibiliothécaire possède les droits d'un lecteur et peut gérer le catalogue de livre ainsi que déclaré le retour d'un emprunt
     *
     * Un(e) Administrateur possède les droits d'un bibliothécaire et peut gérer les utilisateurs de la plateforme
     */
    @Column(name = "AUTHORITY", nullable = false, length = 15)
    @Builder.Default
    private String role = "ROLE_READER";

    /**
     * Cet attribut représente l'accord de l'utilisateur au stockage de ses informations personnelles
     * Nom, Prenom & email.
     */
    @Column( name = "GDPR_AGREEMENT", nullable = false)
    @Builder.Default
    private boolean accordRgpd = false;

}
