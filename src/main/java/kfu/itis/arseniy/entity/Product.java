package kfu.itis.arseniy.entity;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private byte[] image;

    @OneToMany(mappedBy = "product")
    private List<ProductOrder> productOrders;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    public Product(Long id, String name, String description, double price, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
