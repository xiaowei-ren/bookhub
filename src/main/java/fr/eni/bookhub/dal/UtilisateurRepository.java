package fr.eni.bookhub.dal;

import fr.eni.bookhub.bo.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository <Utilisateur, String> {

    Utilisateur findUtilisateurByEmail(String email);

    Utilisateur findUtilisateurByEmailAndPassword(String email, String password);

    String email(String email);
}
