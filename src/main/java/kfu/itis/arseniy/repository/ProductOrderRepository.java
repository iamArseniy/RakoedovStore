package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.entity.Order;
import kfu.itis.arseniy.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findByOrder(Order order);

    @Query("SELECT po FROM ProductOrder po JOIN FETCH po.product WHERE po.order = :order")
    List<ProductOrder> findByOrderWithProducts(@Param("order") Order order);

    @Transactional
    @Modifying
    void deleteByOrder(Order order);


    @Query("SELECT SUM(po.amount) FROM ProductOrder po WHERE po.order = :order")
    Integer sumAmountByOrder(@Param("order") Order order);

    @Query("SELECT SUM(po.totalPrice) FROM ProductOrder po WHERE po.order = :order")
    Double sumTotalPriceByOrder(@Param("order") Order order);
}