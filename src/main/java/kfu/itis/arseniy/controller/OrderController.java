package kfu.itis.arseniy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kfu.itis.arseniy.entity.*;
import kfu.itis.arseniy.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;
    private final ProductOrderService productOrderService;
    private final StageService stageService;
    private final ClientService clientService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, ProductService productService,
                           ProductOrderService productOrderService, StageService stageService, ClientService clientService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.stageService = stageService;
        this.clientService = clientService;
    }

    @GetMapping
    public String getOrderPage(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            logger.warn("Попытка доступа к заказам без авторизации.");
            return "redirect:/login";
        }

        logger.info("Пользователь {} просматривает свои заказы.", email);

        List<Product> products = productService.findAllWithImages();
        List<Stage> stages = stageService.findAll();

        Map<Long, String> productMap = new HashMap<>();
        Map<Long, Stage> stageMap = new HashMap<>();

        for (Product product : products) {
            productMap.put(product.getId(), product.getName());
        }

        for (Stage stage : stages) {
            stageMap.put(stage.getId(), stage);
        }

        List<Order> orders = orderService.findByClient(client);

        model.addAttribute("ordersByClient", orders);
        model.addAttribute("client", client);
        model.addAttribute("productMap", productMap);
        model.addAttribute("stageMap", stageMap);

        return "order";
    }

    @PostMapping
    public String makeOrder(@RequestParam("totalSum") double totalSum,
                            @RequestParam("action") String action,
                            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            logger.warn("Попытка создания заказа без авторизации.");
            return "redirect:/login";
        }

        if ("makeOrder".equals(action)) {
            try {
                List<Cart> cartItems = cartService.findByClient(client);

                List<ProductOrder> productOrders = new ArrayList<>();
                for (Cart cartItem : cartItems) {
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setProduct(cartItem.getProduct());
                    productOrder.setAmount(cartItem.getAmount());
                    productOrder.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getAmount());
                    productOrders.add(productOrder);
                }

                Order order = orderService.createOrder(client, productOrders);

                cartService.deleteByClient(client);

                logger.info("Пользователь {} создал заказ на сумму {}.", client.getEmail(), order.getTotalPrice());

                return "redirect:/order/check/" + order.getId();

            } catch (Exception e) {
                logger.error("Ошибка при создании заказа пользователем {}: {}", client.getEmail(), e.getMessage(), e);
                model.addAttribute("error", "Ошибка при создании заказа: " + e.getMessage());
                return "error";
            }
        }
        return "redirect:/order";
    }


    @GetMapping("/check/{orderId}")
    public String showOrderCheck(@PathVariable Long orderId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            logger.warn("Попытка доступа к чеку без авторизации.");
            return "redirect:/login";
        }

        Order order = orderService.findById(orderId).orElse(null);
        if (order == null || !order.getClient().getId().equals(client.getId())) {
            logger.warn("Попытка доступа к чужому заказу. OrderId: {}", orderId);
            return "redirect:/order";
        }

        List<ProductOrder> productOrders = productOrderService.findByOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("productOrders", productOrders);
        return "order-check";
    }

    @GetMapping("/check/{orderId}/download-pdf")
    public void downloadOrderPdf(@PathVariable Long orderId, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Order order = orderService.findById(orderId).orElse(null);
        if (order == null || !order.getClient().getId().equals(client.getId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<ProductOrder> productOrders = productOrderService.findByOrder(order);

        try {
            Map<String, Object> payload = new HashMap<>();
            List<Map<String, Object>> products = new ArrayList<>();

            for (ProductOrder po : productOrders) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", po.getProduct().getName());
                item.put("amount", po.getAmount());
                item.put("price", po.getProduct().getPrice());
                products.add(item);
            }

            payload.put("products", products);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF));


            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
            ResponseEntity<byte[]> pdfResponse = restTemplate.postForEntity("http://localhost:5001/generate-pdf", entity, byte[].class);

            if (pdfResponse.getStatusCode() == HttpStatus.OK) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=order_" + orderId + ".pdf");
                OutputStream out = response.getOutputStream();
                out.write(Objects.requireNonNull(pdfResponse.getBody()));
                out.flush();
            } else {
                logger.error("PDF-сервис вернул статус {}", pdfResponse.getStatusCode());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            logger.error("Ошибка при запросе PDF-сервиса: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
