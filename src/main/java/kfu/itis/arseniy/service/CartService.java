package kfu.itis.arseniy.service;

import kfu.itis.arseniy.entity.Cart;
import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.entity.Product;
import kfu.itis.arseniy.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    public Optional<Cart> findById(Long id) {
        logger.debug("Поиск корзины по ID: {}", id);
        return cartRepository.findById(id);
    }

    public List<Cart> findByClient(Client client) {
        logger.debug("Получение корзины клиента: {}", client.getEmail());
        return cartRepository.findByClient(client);
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Удаление корзины с ID: {}", id);
        cartRepository.deleteById(id);
    }

    @Transactional
    public void deleteByClient(Client client) {
        logger.info("Удаление всех корзин клиента: {}", client.getEmail());
        cartRepository.deleteByClient(client);
    }

    public Cart create(Cart cart) {
        logger.info("Создание новой корзины: клиент {}, товар {}", cart.getClient().getEmail(), cart.getProduct().getName());
        return cartRepository.save(cart);
    }

    public List<Cart> findAll() {
        logger.debug("Получение всех корзин");
        return cartRepository.findAll();
    }

    @Transactional
    public void removeItem(Client client, Product product) {
        logger.info("Удаление товара {} из корзины клиента {}", product.getName(), client.getEmail());
        cartRepository.removeItem(client, product);
    }

    @Transactional
    public void addToCart(Client client, Product product, int amount) {
        Optional<Cart> existingCartItem = cartRepository.findByClientAndProduct(client, product);

        if (existingCartItem.isPresent()) {
            logger.info("Добавление {} шт. товара {} в существующую корзину клиента {}", amount, product.getName(), client.getEmail());
            cartRepository.addToCart(client, product, amount);
        } else {
            logger.info("Создание новой записи в корзине: {} шт. товара {} для клиента {}", amount, product.getName(), client.getEmail());
            Cart newCartItem = new Cart(client, product, amount);
            cartRepository.save(newCartItem);
        }
    }

    public Integer getAmount(Client client, Product product) {
        logger.debug("Получение количества товара {} у клиента {}", product.getName(), client.getEmail());
        return cartRepository.getAmount(client, product);
    }

    public Optional<Cart> findByClientAndProduct(Client client, Product product) {
        logger.debug("Поиск корзины по клиенту {} и товару {}", client.getEmail(), product.getName());
        return cartRepository.findByClientAndProduct(client, product);
    }
}
