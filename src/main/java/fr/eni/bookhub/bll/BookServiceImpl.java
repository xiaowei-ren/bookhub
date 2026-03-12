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

    /**
     * Injection du dépôt via le constructeur (bonne pratique Spring).
     * @param bookRepository Le dépôt de données des livres.
     */
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Récupère tous les livres disponibles.
     * @return
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Récupère un livre par son ISBN via le repository.
     */
    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    /**
     * Recherche les livres dont le titre contient le mot-clé (insensible à la casse).
     */
    @Override
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    /**
     * Persiste ou met à jour le livre en base de données.
     */
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Supprime le livre correspondant à l'ISBN fourni.
     */
    @Override
    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }
}