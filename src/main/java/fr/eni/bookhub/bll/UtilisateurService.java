package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.bo.UtilisateurReponse;

public interface UtilisateurService {

    UtilisateurReponse ajouterUtilisateur(Utilisateur utilisateur);

}
