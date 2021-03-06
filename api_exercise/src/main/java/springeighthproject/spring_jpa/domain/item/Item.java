package springeighthproject.spring_jpa.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springeighthproject.spring_jpa.domain.Category;
import springeighthproject.spring_jpa.domain.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    public Item(String name, int price, int stockQuantity, List<Category> categories) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categories = categories;
    }

    //== 비즈니스 로직 ==//
    //재고 수량 증가 로직
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    //재고 수량 감소 로직
    public void removeStock(int quantity){
        int restStock = this.stockQuantity -= quantity;
        if(restStock <0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }


}
