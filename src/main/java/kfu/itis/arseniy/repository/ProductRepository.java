package kfu.itis.arseniy.repository;

import kfu.itis.arseniy.dto.ProductInfoDto;
import kfu.itis.arseniy.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query("SELECT new kfu.itis.arseniy.dto.ProductInfoDto(p.id, p.name, p.description, p.price) FROM Product p")
    List<ProductInfoDto> findAllProductInfo();
}