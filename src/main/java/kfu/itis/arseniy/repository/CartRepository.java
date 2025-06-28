package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.entity.Cart;
import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByClient(Client client);

    Optional<Cart> findByClientAndProduct(Client client, Product product);

    @Transactional
    void deleteByClient(Client client);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.client = ?1 AND c.product = ?2")
    void removeItem(Client client, Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.amount = c.amount + ?3 WHERE c.client = ?1 AND c.product = ?2")
    void addToCart(Client client, Product product, int amount);

    @Query("SELECT c.amount FROM Cart c WHERE c.client = ?1 AND c.product = ?2")
    Integer getAmount(Client client, Product product);
}