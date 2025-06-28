package kfu.itis.arseniy.service;

import kfu.itis.arseniy.entity.Product;
import kfu.itis.arseniy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        logger.info("Поиск продукта по ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Продукт с ID {} не найден", id);
                    return new RuntimeException("Product not found");
                });
        logger.info("Продукт с ID {} найден: {}", id, product.getName());
        return product;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findByIdWithImage(Long id) {
        logger.info("Поиск продукта с изображением по ID: {}", id);
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllWithImages() {
        logger.info("Получение всех продуктов с изображениями");
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(String name, String description,
                                 double price, MultipartFile imageFile) throws IOException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        product.setImage(imageFile.getBytes());

        Product savedProduct = productRepository.save(product);
        logger.info("Продукт создан с ID: {}", savedProduct.getId());
        return savedProduct;
    }

    @Transactional
    public void updateProductImage(Long productId, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
        product.setImage(imageFile.getBytes());
        productRepository.save(product);
        logger.info("Изображение продукта с ID {} успешно обновлено", productId);
    }


}