package fr.eni.bookhub.bo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "bookhub_book")
public class Book {

    @Id
    @Column(name = "isbn", length = 13)
    private String isbn;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 250)
    private String author;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "nb_copies", nullable = false)
    private int nbCopies;

    @Column(name = "cover",  length = 500)
    private String coverImage; // URL ou chemin vers l'image

    // Relation ManyToMany : un livre peut avoir plusieurs catégories
    // Utilisation d'une table de jointure 'book_categorie'
    @ManyToMany
    @JoinTable(
            name = "bookhub_book_category",
            joinColumns = @JoinColumn(name = "book_isbn"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Categorie> categories;
}
