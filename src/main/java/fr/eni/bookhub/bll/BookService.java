package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Book;
import java.util.List;
import java.util.Optional;

/**
 * Interface définissant les opérations métier relatives à la gestion des livres.
 */
public interface BookService {

    /**
     * Récupère tous les livres disponibles.
     * @return Une liste de tous les livres.
     */
    List<Book> findAll();

    /**
     * Récupère un livre spécifique à partir de son numéro ISBN.
     * @param isbn Le numéro ISBN du livre.
     * @return Un Optional contenant le livre s'il est trouvé, sinon vide.
     */
    Optional<Book> getBookByIsbn(String isbn);

    /**
     * Recherche des livres basés sur un mot-clé (titre ou contenu).
     * @param keyword Le mot-clé à rechercher.
     * @return Une liste de livres correspondant au mot-clé.
     */
    List<Book> searchBooks(String keyword);

    /**
     * Enregistre ou met à jour un livre dans la base de données.
     * @param book L'objet livre à sauvegarder.
     * @return Le livre sauvegardé.
     */
    Book saveBook(Book book);

    /**
     * Supprime un livre identifié par son ISBN.
     * @param isbn Le numéro ISBN du livre à supprimer.
     */
    void deleteBook(String isbn);
}