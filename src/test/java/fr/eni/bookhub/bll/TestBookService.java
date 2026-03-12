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

    @Test
    void testFindAll_ReturnsListOfBooks() {
        // Mocking du repository pour retourner une liste de livres
        List<Book> mockBooks = Arrays.asList(
                new Book(
                        "9781338299144",
                        "harry potter and the sorcerer's stone",
                        "JK Rowling",
                        "Roman",
                        "Description du livre",
                        100,
                        "https://images-na.ssl-images-amazon.com/images/I/51UoqwM9sDL._SX331_BO1,204,203,200_.jpg"
                ),
                new Book(
                        "1234567890123",
                        "Hugo and the Fire Cup",
                        "John Doe",
                        "Documentaire",
                        "Description du document",
                        20,
                        "https://placekittens.com/200/300"
                )
        );
        when(bookRepository.findAll()).thenReturn(mockBooks);

        // WHEN
        List<Book> result = bookService.findAll();

        // THEN
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookByIsbn_Found() {
        // GIVEN
        Book book = new Book(
                "1234567890123",
                "Java Guide",
                "John Doe",
                "Programmation",
                "Description du livre",
                100,
                "https://placekittens.com/200/300"
        );
        when(bookRepository.findById("123")).thenReturn(Optional.of(book));

        // WHEN
        Optional<Book> result = bookService.getBookByIsbn("123");

        // THEN
        assertTrue(result.isPresent());
        assertEquals("Java Guide", result.get().getTitle());
    }
}