package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.bo.UtilisateurReponse;
import fr.eni.bookhub.dal.UtilisateurRepository;
import fr.eni.bookhub.exceptions.BusinessCode;
import fr.eni.bookhub.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Le service Utilisateur est responsable de l'ajout de nouveaux utilisateurs,
 * et de la mise à jour des informations d'un utilisateur existant.
 * Ce service n'est PAS responsable de l'authentification des utilisateurs
 */
@Slf4j
@AllArgsConstructor
@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UtilisateurReponse ajouterUtilisateur(Utilisateur utilisateur) {

        BusinessException be = new BusinessException();
        boolean isValid = true;
        isValid &= validerUniqueEmail(utilisateur.getEmail(), be);


        if (isValid) {
            try {
                utilisateur.setRole("ROLE_READER");
                utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
                Utilisateur utilisateurBD = utilisateurRepository.save(utilisateur);
                UtilisateurReponse utilisateurReponse = new UtilisateurReponse(utilisateurBD.getEmail(), utilisateurBD.getNom(), utilisateurBD.getPrenom());
                return utilisateurReponse;
            } catch (RuntimeException err){
                log.error("Erreur lors de la sauvegarde de l'utilisateur {}", utilisateur.getEmail(), err);
                be.add(BusinessCode.REPOSITORY_UTILISATEUR_SAVE_ERREUR);
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
