package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Book;
import fr.eni.bookhub.dal.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service gérant la logique métier des livres.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllAvailable() {
        return List.copyOf(bookRepository.findByNbCopiesGreaterThan(0)); // Retourne uniquement les livres avec au moins 1 exemplaire
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public List<Book> searchByTitleOrAuthor(String keyword) {
        return bookRepository.findByNbCopiesGreaterThanAndTitleContainingIgnoreCaseOrNbCopiesGreaterThanAndAuthorContainingIgnoreCase(
                0, keyword, 0, keyword); // Recherche uniquement parmi les livres disponibles
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(String isbn) {
        bookRepository.findById(isbn).ifPresent(book -> {
            book.setNbCopies(0); // Marquer le livre comme supprimé en mettant le nombre d'exemplaires à 0
            bookRepository.save(book);
        });

    }
}