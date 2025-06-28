package kfu.itis.arseniy.service;

import kfu.itis.arseniy.entity.*;
import kfu.itis.arseniy.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductOrderRepository productOrderRepository;
    private final ProductService productService;
    private final OrderService orderService;

    @Transactional
    public ProductOrder createFromCart(Cart cart, Order order) {
        logger.info("Создание ProductOrder из корзины для заказа с ID: {}", order.getId());
        Product product = productService.findById(cart.getProduct().getId());

        double totalPrice = product.getPrice() * cart.getAmount();
        logger.info("ProductOrder сохранен с ID: {}", product.getId());
        return productOrderRepository.save(
                new ProductOrder(order, product, cart.getAmount(), totalPrice)
        );
    }

    @Transactional(readOnly = true)
    public List<ProductOrder> findByOrder(Order order) {
        logger.info("Получение ProductOrder по заказу ID: {}", order.getId());
        return productOrderRepository.findByOrderWithProducts(order);
    }

    @Transactional
    public void deleteByOrder(Order order) {
        logger.info("Удаление ProductOrder по заказу ID: {}", order.getId());
        productOrderRepository.deleteByOrder(order);
    }

    @Transactional(readOnly = true)
    public Double calculateOrderTotal(Order order) {
        logger.info("Подсчет количества товаров в заказе ID: {}", order.getId());

        return productOrderRepository.sumTotalPriceByOrder(order);
    }

    @Transactional(readOnly = true)
    public Integer countProductsInOrder(Order order) {
        logger.info("Подсчет количества товаров в заказе ID: {}", order.getId());

        return productOrderRepository.sumAmountByOrder(order);
    }
    @Transactional
    public List<ProductOrder> createAll(List<ProductOrder> productOrders) {
        logger.info("Создание списка ProductOrder. Количество: {}", productOrders.size());
        return productOrderRepository.saveAll(productOrders);
    }
}