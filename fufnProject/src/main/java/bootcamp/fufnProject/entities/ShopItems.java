package bootcamp.fufnProject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "shop_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "picture_url")
    private String picture_url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Brands brand;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Categories> categories;

}

