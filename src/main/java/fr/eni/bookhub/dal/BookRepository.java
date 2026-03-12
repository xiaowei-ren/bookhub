package fr.eni.bookhub.dal;
import fr.eni.bookhub.bo.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    // Spring Data JPA génère la requête SQL automatiquement grâce à ce nom de méthode
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}
