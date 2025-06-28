package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.entity.Order;
import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    List<Order> findByClient(Client client);

    List<Order> findByStage(Stage stage);

    List<Order> findByClientAndStage(Client client, Stage stage);


    List<Order> findByCreatedAtAfter(Timestamp date);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.stage = ?2, o.updatedAt = CURRENT_TIMESTAMP WHERE o.id = ?1")
    void updateOrderStage(Long orderId, Stage newStage);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.totalPrice = ?2, o.updatedAt = CURRENT_TIMESTAMP WHERE o.id = ?1")
    void updateOrderTotalPrice(Long orderId, double newTotalPrice);


    @Transactional
    default Order createOrderWithReturn(Order order) {
        return save(order);
    }
}