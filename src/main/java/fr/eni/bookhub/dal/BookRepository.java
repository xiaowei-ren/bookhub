package fr.eni.bookhub.dal;

import fr.eni.bookhub.bo.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCategory(String category);
    List<Book> findByTitleContainingIgnoreCase(String title);

}
