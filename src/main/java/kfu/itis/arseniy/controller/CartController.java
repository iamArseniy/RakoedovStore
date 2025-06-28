package kfu.itis.arseniy.controller;

import kfu.itis.arseniy.entity.Cart;
import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.entity.Product;
import kfu.itis.arseniy.service.CartService;
import kfu.itis.arseniy.service.ClientService;
import kfu.itis.arseniy.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;
    private final ProductService productService;
    private final ClientService clientService;

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("Неаутентифицированный доступ к корзине");
            return "redirect:/login";
        }

        String email = authentication.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            logger.warn("Клиент с email {} не найден", email);
            return "redirect:/login";
        }

        List<Cart> cartItems = cartService.findByClient(client);
        List<Product> products = productService.findAllWithImages();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("products", products);

        logger.info("Показ корзины для клиента: {}", email);

        return "cart";
    }

    @PostMapping
    public ResponseEntity<?> updateCart(@RequestParam("product_id") Long productId,
                                        @RequestParam("quantity") Integer quantity,
                                        @RequestParam(value = "action", required = false) String action,
                                        HttpServletRequest request) {

        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            if (isAjax) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Вы не авторизованы");
                return ResponseEntity.status(401).body(error);

            }
            return ResponseEntity.status(302).header("Location", "/login").build();
        }

        String email = authentication.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            if (isAjax) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Клиент не найден");
                return ResponseEntity.status(404).body(error);
            }
            return ResponseEntity.status(302).header("Location", "/login").build();
        }

        Product product = productService.findById(productId);

        if ("remove".equals(action)) {
            cartService.removeItem(client, product);
            return isAjax ? ResponseEntity.ok().build() : ResponseEntity.status(302).header("Location", "/cart").build();
        } else {
            cartService.addToCart(client, product, quantity);
            return isAjax ? ResponseEntity.ok().build() : ResponseEntity.status(302).header("Location", "/cart").build();
        }
    }


}
