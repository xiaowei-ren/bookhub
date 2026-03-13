package fr.eni.bookhub.bll;

import fr.eni.bookhub.bo.Book;
import fr.eni.bookhub.dal.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestBookService {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    // 1. Test de la méthode findAll
    @Test
    void testFindAll_ReturnsListOfBooks() {
        List<Book> mockBooks = Arrays.asList(Book.builder().isbn("1235739011235").build(), Book.builder().isbn("456").build());
        when(bookRepository.findAll()).thenReturn(mockBooks);

        List<Book> result = bookService.findAll();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    //  2. Test de la méthode getBookByIsbn

    @Test
    void testGetBookByIsbn_ReturnsBook() {
        String isbn = "1234567890123";
        Book mockBook = Book.builder().isbn(isbn).title("Java Guide").build();
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(mockBook));

        Optional<Book> result = bookService.getBookByIsbn(isbn);

        assertTrue(result.isPresent());
        assertEquals("Java Guide", result.get().getTitle());
    }

    // 3. Test de la méthode getBookByIsbn (cas où le livre est introuvable)
    @Test
    void testGetBookByIsbn_ReturnsEmpty() {
        String isbn = "1234567890123";
        when(bookRepository.findById(isbn)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.getBookByIsbn(isbn);

        assertFalse(result.isPresent());
        verify(bookRepository, times(1)).findById(isbn);
    }

    // 4. Test de la méthode de recherche multicritère
    @Test
    void testSearchByTitleOrAuthor_ReturnsListOfBooks() {

        String keyword = "Java";
        int minCopies = 0;

        List<Book> mockBooks = Arrays.asList(
                Book.builder().isbn("123566790").nbCopies(5).build(), // 确保测试数据本身是“可用”的
                Book.builder().isbn("456").nbCopies(2).build()
        );


        when(bookRepository.findByNbCopiesGreaterThanAndTitleContainingIgnoreCaseOrNbCopiesGreaterThanAndAuthorContainingIgnoreCase(
                minCopies, keyword, minCopies, keyword))
                .thenReturn(mockBooks);


        List<Book> result = bookService.searchByTitleOrAuthor(keyword);


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getNbCopies());


        verify(bookRepository, times(1)).findByNbCopiesGreaterThanAndTitleContainingIgnoreCaseOrNbCopiesGreaterThanAndAuthorContainingIgnoreCase(
                minCopies, keyword, minCopies, keyword);
    }

    // 5. Test de la méthode saveBook
    @Test
    void testSaveBook_ReturnsSavedBook() {
        Book book = Book.builder().isbn("123").build();
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.saveBook(book);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }

    // 6. Test de la méthode deleteBook
    @Test
    void testDeleteBook_VerifiesDeletion() {
        String isbn = "1234567890123";
        Book mockBook = Book.builder().isbn(isbn).nbCopies(5).build();
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(mockBook));

        bookService.deleteBook(isbn);

        assertEquals(0, mockBook.getNbCopies());
        verify(bookRepository, times(1)).save(mockBook);
    }
}