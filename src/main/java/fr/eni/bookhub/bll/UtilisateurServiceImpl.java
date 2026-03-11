package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.dal.UtilisateurRepository;
import fr.eni.bookhub.exceptions.BusinessCode;
import fr.eni.bookhub.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Le service Utilisateur est responsable de l'ajout de nouveaux utilisateurs,
 * et de la mise à jour des informations d'un utilisateur existant.
 * Ce service n'est PAS responsable de l'authentification des utilisateurs
 */

@AllArgsConstructor
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {

        BusinessException be = new BusinessException();
        boolean isValid = true;
        isValid &= validerUniqueEmail(utilisateur.getEmail(), be);


        if (isValid) {
            try {
                utilisateur.setRole("ROLE_READER");
                utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
                return utilisateurRepository.save(utilisateur);
            } catch (RuntimeException err){
                be.add(BusinessCode.REPOSITORY_UTILISATEUR_SAVE_ERREUR + utilisateur);
                throw be;
            }
        } else {
            throw be;
        }
    }

    /**
     * Vérifie qu'aucun utilisateur existant ne possède déjà l'email fourni.
     * En cas de doublon, ajoute un code d'erreur métier dans l'exception fournie.
     *
     * @param email email à vérifier
     * @param be conteneur d'erreurs métier
     * @return true si l'email est disponible, false sinon
     */
    private boolean validerUniqueEmail(String email, BusinessException be) {
        Utilisateur utilisateurBD = utilisateurRepository.findUtilisateurByEmail(email);
        if (utilisateurBD != null ) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_EMAIL);
            return false;
        }
        return true;
    }

}
