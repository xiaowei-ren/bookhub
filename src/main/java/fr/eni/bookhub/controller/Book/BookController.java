package fr.eni.bookhub.controller.Book;

import fr.eni.bookhub.bll.BookService;
import fr.eni.bookhub.bo.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * ADMIN ONLY : Récupère TOUS les livres (y compris ceux à 0 exemplaire).
     */
    @GetMapping("/admin/all")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    /**
     * PUBLIC : Récupère uniquement les livres disponibles dans le catalogue (nbCopies > 0).
     */
    @GetMapping
    public List<Book> findAllAvailable() {
        return bookService.findAllAvailable();
    }

    /**
     * PUBLIC : Recherche uniquement parmi les livres disponibles au catalogue.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        // On utilise la méthode filtrée pour ne pas afficher de livres à 0 exemplaire
        return ResponseEntity.ok(bookService.searchByTitleOrAuthor(keyword));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    /**
     * LOGIQUE DE SUPPRESSION : Effectue une suppression logique (nbCopies = 0).
     */
    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
    }
}