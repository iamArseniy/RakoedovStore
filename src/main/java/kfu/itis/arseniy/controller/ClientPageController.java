package kfu.itis.arseniy.controller;

import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.entity.Order;
import kfu.itis.arseniy.entity.Product;
import kfu.itis.arseniy.entity.ProductOrder;
import kfu.itis.arseniy.service.ClientService;
import kfu.itis.arseniy.service.OrderService;
import kfu.itis.arseniy.service.ProductOrderService;
import kfu.itis.arseniy.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ClientPageController {

    private static final Logger logger = LoggerFactory.getLogger(ClientPageController.class);

    private final OrderService orderService;
    private final ProductOrderService productOrderService;
    private final ProductService productService;
    private final ClientService clientService;

    @GetMapping("/clientPage")
    public String showClientPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("Неаутентифицированный доступ к клиентской странице");
            return "redirect:/login";
        }

        String email = authentication.getName();
        logger.info("Попытка доступа к клиентской странице: {}", email);

        Client client = clientService.findByEmail(email).orElse(null);
        if (client == null) {
            logger.warn("Клиент с email {} не найден", email);
            return "redirect:/login";
        }

        List<Product> products = productService.findAllWithImages();
        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }

        List<Order> orders = orderService.findByClient(client);
        logger.info("У клиента {} найдено заказов: {}", email, orders.size());

        for (Order order : orders) {
            List<ProductOrder> productOrders = productOrderService.findByOrder(order);
            order.setProductOrders(productOrders);
        }

        model.addAttribute("ordersByClient", orders);
        model.addAttribute("client", client);
        model.addAttribute("products", products);
        model.addAttribute("productMap", productMap);

        return "clientPage";
    }
}
