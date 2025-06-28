package kfu.itis.arseniy.entity;


import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int amount;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    public ProductOrder(Order order, Product product, int amount, Double totalPrice) {
        this.order = order;
        this.product = product;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }
}
