package fr.eni.bookhub.bo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @Column(name = "isbn", length = 13)
    private String isbn;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 250)
    private String author;

    @Column(name = "category", nullable = false, length = 150)
    private String category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "nb_copies", nullable = false)
    private int nbCopies;

    @Column(name = "cover_url", length = 500)
    private String coverUrl;
}
