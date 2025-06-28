package kfu.itis.arseniy.service;


import kfu.itis.arseniy.entity.*;
import kfu.itis.arseniy.repository.OrderRepository;
import kfu.itis.arseniy.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ClientService clientService;
    private final StageService stageService;

    @Transactional
    public Order createOrder(Client client, List<ProductOrder> productOrders) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now.withNano(0));

        Order order = new Order();
        order.setClient(client);
        order.setStage(stageService.getInitialStage());
        order.setCreatedAt(timestamp);
        order.setUpdatedAt(timestamp);

        double totalPrice = productOrders.stream()
                .mapToDouble(po -> po.getProduct().getPrice() * po.getAmount())
                .sum();

        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);

        productOrders.forEach(po -> po.setOrder(savedOrder));
        productOrderRepository.saveAll(productOrders);

        logger.info("Создан новый заказ ID={} клиентом {} на сумму {}",
                savedOrder.getId(), client.getEmail(), totalPrice);

        return savedOrder;
    }

    public Optional<Order> findById(Long id) {
        logger.debug("Поиск заказа по ID: {}", id);
        return orderRepository.findById(id);
    }

    public List<Order> findByClient(Client client) {
        logger.debug("Получение заказов клиента: {}", client.getEmail());
        return orderRepository.findByClient(client);
    }

    @Transactional
    public void updateOrderStage(Long orderId, Stage newStage) {
        logger.info("Обновление стадии заказа {} до {}", orderId, newStage.getName());
        orderRepository.updateOrderStage(orderId, newStage);
    }

    public List<Order> findByStage(Stage stage) {
        logger.debug("Получение заказов на стадии: {}", stage.getName());
        return orderRepository.findByStage(stage);
    }
}
