package fr.eni.bookhub.controller.Book;

import fr.eni.bookhub.bll.BookService;
import fr.eni.bookhub.bo.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations sur les livres.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    // Injection du service via le constructeur
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Récupère tous les livres.
     * GET /api/books
     */
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    /**
     * Récupère un livre par son ISBN.
     * GET /api/books/{isbn}
     */
    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Ajoute ou met à jour un livre.
     * POST /api/books
     */
    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    /**
     * Rechercher par titre ou par auteur .
     * GET /api/books/search?keyword=mot-clé
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        List<Book> books = bookService.searchByTitleOrAuthor(keyword);
        return ResponseEntity.ok(books);
    }

    /**
     * Supprime un livre par son ISBN.
     * DELETE /api/books/{isbn}
     */
    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
    }
}
