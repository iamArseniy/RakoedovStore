package kfu.itis.arseniy.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    public Cart(Client client, Product product, int amount) {
        this.client = client;
        this.product = product;
        this.amount = amount;
    }
}
