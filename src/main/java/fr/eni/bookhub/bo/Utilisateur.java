package fr.eni.bookhub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Data
@EqualsAndHashCode(of = {"email"})
@Entity
@Table( name = "BOOKHUB_USER")
public class Utilisateur {

    @Id
    @Column( nullable = false, length = 250)
    private String email;

    @Column ( name = "LAST_NAME", nullable = false, length = 150)
    private String nom;

    @Column(name = "FIRST_NAME", nullable = false, length = 150)
    private String prenom;

    @ToString.Exclude
    @Column (nullable = false, length = 255)
    private String password;

    @Column(name = "AUTHORITY", nullable = false, length = 15)
    private String role;

    @Column( name = "GDPR_AGREEMENT", nullable = false)
    private boolean accordRgpd = false;

}
