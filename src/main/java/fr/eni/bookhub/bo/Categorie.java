package fr.eni.bookhub.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookhub_category", schema = "dbo")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private int idCategory;

    @Column(name = "name_category", nullable = false)
    private String nomCategory;

    // Mapping inverse, relation ManyToMany
    // L'attribut 'mappedBy' fait référence au champ 'categorie' dans la classe Livre
    @ManyToMany(mappedBy = "categories")
    @JsonIgnore //  Pour éviter la récursion
    private List<Book> books;
}