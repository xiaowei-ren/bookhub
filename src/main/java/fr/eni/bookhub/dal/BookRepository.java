package fr.eni.bookhub.dal;

import fr.eni.bookhub.bo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    // Récupère tous les livres disponibles (exemplaires > 0) pour le catalogue public
    List<Book> findByNbCopiesGreaterThan(Integer count);


    // Optimisation : Recherche intelligente filtrant uniquement les livres disponibles
    // Utilise la syntaxe 'And' de Spring Data JPA pour combiner les conditions
    List<Book> findByNbCopiesGreaterThanAndTitleContainingIgnoreCaseOrNbCopiesGreaterThanAndAuthorContainingIgnoreCase(
            Integer countTitle, String title, Integer countAuthor, String author);
}