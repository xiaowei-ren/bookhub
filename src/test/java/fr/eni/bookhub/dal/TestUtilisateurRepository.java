package fr.eni.bookhub.dal;

import fr.eni.bookhub.bo.Utilisateur;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test unitaires du repository Utilisateur.
 * Ils valident les opérations de persistance et de recherche
 * sur l'entité Utilisateur.
 */
@DataJpaTest
public class TestUtilisateurRepository {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EntityManager entityManager;


    Utilisateur utilisateur;

    void initialiseUtilisateur() {
        utilisateur = Utilisateur.builder()
                .email("charles@email.com")
                .nom("Tonneau")
                .prenom("Charles")
                .password("Hez67@C1oz!Aci24")
                .accordRgpd(true)
                .build();
    }

    void initialiseBD () {
        entityManager.persist(utilisateur);
        entityManager.flush();
        Utilisateur utilisateurBD = entityManager.find(Utilisateur.class, "charles@email.com");
        assertThat(utilisateurBD).isNotNull();
    }

    @BeforeEach
    void initialiseDonnees() {
        initialiseUtilisateur();
    }

    /**
     * Vérification de l'ajout d'un nouvel utilisateur à la base de données.
     */
    @Test
    public void testAjoutUtilisateur() {
        //Arrange

        //Act
        utilisateurRepository.save(utilisateur);

        //Assert
        Utilisateur utilisateurDB = entityManager.find(Utilisateur.class, "charles@email.com");

        assertThat(utilisateurDB).isNotNull();
        assertThat(utilisateurDB.getNom()).isEqualTo(utilisateur.getNom());
        assertThat(utilisateurDB.getPrenom()).isEqualTo(utilisateur.getPrenom());
        assertThat(utilisateurDB.getRole()).isEqualTo("ROLE_READER");
        assertThat(utilisateurDB.isAccordRgpd()).isEqualTo(utilisateur.isAccordRgpd());
    }

    /**
     * Vérification de la supression d'un utilisateur à la base de données.
     */
    @Test
    public void testSupprimerUtilisateur() {
        //Arrange
        initialiseBD();

        //Act
        utilisateurRepository.delete(utilisateur);

        //Assert
        Utilisateur utilisateurBD = entityManager.find(Utilisateur.class, "charles@email.com");
        assertThat(utilisateurBD).isNull();

    }

    /**
     * Vérification de la recherche d'un utilisateur par l'email.
     */
    @Test
    public void testRechercheParEmail() {
        //Arrange
        initialiseBD();

        //Act
        Utilisateur utilisateurTrouve = utilisateurRepository.findUtilisateurByEmail("charles@email.com");

        //Assert
        assertThat(utilisateurTrouve).isNotNull();
        assertThat(utilisateurTrouve.getNom()).isEqualTo(utilisateur.getNom());
        assertThat(utilisateurTrouve.getPrenom()).isEqualTo(utilisateur.getPrenom());
        assertThat(utilisateurTrouve.getRole()).isEqualTo(utilisateur.getRole());
        assertThat(utilisateurTrouve.isAccordRgpd()).isEqualTo(utilisateur.isAccordRgpd());
    }

    /**
     * Vérification de la recherche d'un utilisateur par l'email & mot de passe.
     */
    @Test
    public void testRechercheParEmailEtPassword() {
        //Arrange
        initialiseBD();

        //Act
        Utilisateur utilisateurTrouve = utilisateurRepository.findUtilisateurByEmailAndPassword("charles@email.com", "Hez67@C1oz!Aci24");

        //Assert
        assertThat(utilisateurTrouve).isNotNull();
        assertThat(utilisateurTrouve.getNom()).isEqualTo(utilisateur.getNom());
        assertThat(utilisateurTrouve.getPrenom()).isEqualTo(utilisateur.getPrenom());
        assertThat(utilisateurTrouve.getRole()).isEqualTo(utilisateur.getRole());
        assertThat(utilisateurTrouve.isAccordRgpd()).isEqualTo(utilisateur.isAccordRgpd());
    }


}
