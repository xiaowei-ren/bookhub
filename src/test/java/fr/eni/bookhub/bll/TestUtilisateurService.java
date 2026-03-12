package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Utilisateur;
import fr.eni.bookhub.dal.UtilisateurRepository;
import fr.eni.bookhub.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


/**
 * Test unitaires du services Utilisateur.
 * Ils valident les opérations d'ajout d'un nouvel utilisateur
 * et de mise à jour d'un utilisateur existant.
 */
@ExtendWith(MockitoExtension.class)
public class TestUtilisateurService {

    private UtilisateurService utilisateurService;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void initialisation(){
        MockitoAnnotations.openMocks(this);
        utilisateurService = new UtilisateurServiceImpl(utilisateurRepository, passwordEncoder);
    }

    /**
     * Vérifie qu'un utilisateur valide peut être ajouté.
     * Le mot de passe doit être encodé avant la sauvegarde en base.
     */
    @Test
    void test01_ajouter_utilisateur_toutParamValides() {
        //Arrange
        String email = "charles@email.com";

        final Utilisateur utilisateur = Utilisateur.builder()
               .email(email)
               .nom("Tonneau")
               .prenom("Charles")
               .password("Hez67@C1oz!Aci24")
               .accordRgpd(true)
               .build();

        when(passwordEncoder.encode("Hez67@C1oz!Aci24")).thenReturn("hashedPassword");
        when(utilisateurRepository.save(utilisateur)).thenReturn(utilisateur);
        when(utilisateurRepository.findById(email)).thenReturn(Optional.of(utilisateur));

        //Act
        utilisateurService.ajouterUtilisateur(utilisateur);

        //Assert
        Optional<Utilisateur> op = utilisateurRepository.findById(email);
        Utilisateur utilisateurBD = op.get();
        assertNotNull(utilisateurBD);
        assertThat(utilisateurBD.getEmail()).isEqualTo(utilisateur.getEmail());
        assertThat(utilisateurBD.getNom()).isEqualTo(utilisateur.getNom());
        assertThat(utilisateurBD.getPrenom()).isEqualTo(utilisateur.getPrenom());
        assertThat(utilisateurBD.getPassword()).isEqualTo("hashedPassword");

    }

    /**
     * Vérifie qu'un utilisateur ayant un email déjà présent en base de données ne peut être ajouté.
     * Une exception doit être renvoyée.
     */
    @Test
    void test_ajouter_existant() {
        //Arrange
        final Utilisateur utilisateur1 = Utilisateur.builder()
                .email("charles@email.com")
                .nom("Tonneau")
                .prenom("Charles")
                .password("Hez67@C1oz!Aci24")
                .accordRgpd(true)
                .build();

//        when(passwordEncoder.encode("Hez67@C1oz!Aci24")).thenReturn("hashedPassword");
        when(utilisateurRepository.findUtilisateurByEmail("charles@email.com")).thenReturn(utilisateur1);

        final Utilisateur utilisateur2 = Utilisateur.builder()
                .email("charles@email.com")
                .nom("Baker")
                .prenom("Bob")
                .password("67@C1oz!Aci24")
                .accordRgpd(true)
                .build();

        //Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> utilisateurService.ajouterUtilisateur(utilisateur2));
        System.out.println("le message d'erreur est : " + exception.getClefsExternalisations());
    }

}
