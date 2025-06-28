package kfu.itis.arseniy.entity;

import javax.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id",  nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name="stage_id",  nullable = false)
    private Stage stage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders;

    public Order(Client client, Stage stage, Timestamp createdAt, Timestamp updatedAt, double totalPrice) {
        this.client = client;
        this.stage = stage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalPrice = totalPrice;
    }

    public Order(Long id, Client client, Stage stage, Timestamp createdAt, Timestamp updatedAt, double totalPrice) {
        this.id = id;
        this.client = client;
        this.stage = stage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalPrice = totalPrice;
    }
}
