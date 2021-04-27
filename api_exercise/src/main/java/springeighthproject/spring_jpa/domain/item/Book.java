package springeighthproject.spring_jpa.domain.item;

import lombok.*;
import springeighthproject.spring_jpa.domain.Category;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
@NoArgsConstructor
public class Book extends Item{

    private String author;
    private String isbn;

    @Builder
    public Book(String name, int price, int stockQuantity, List<Category> categories, String author, String isbn) {
        super(name, price, stockQuantity, categories);
        this.author = author;
        this.isbn = isbn;
    }
}
