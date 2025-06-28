package kfu.itis.arseniy.service;

import javax.servlet.http.HttpSession;
import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.repository.ClientRepository;
import kfu.itis.arseniy.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kfu.itis.arseniy.exception.EmailAlreadyExistException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public Client create(Client client) {
        logger.info("Создание нового клиента: {}", client.getEmail());
        return clientRepository.save(client);
    }

    @Transactional
    public void register(Client client) throws EmailAlreadyExistException {
        if (existsByEmail(client.getEmail())) {
            logger.warn("Регистрация прервана — email {} уже занят", client.getEmail());
            throw new EmailAlreadyExistException("Клиент с таким email уже существует.");
        }

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(client);
        logger.info("Клиент зарегистрирован: {}", client.getEmail());
    }

    public Optional<Client> findByEmail(String email) {
        logger.debug("Поиск клиента по email: {}", email);
        return clientRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        logger.debug("Проверка существования клиента с email: {}", email);
        return clientRepository.existsByEmail(email);
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public Optional<Client> findById(Long id) {
        logger.debug("Поиск клиента по ID: {}", id);
        return clientRepository.findById(id);
    }

    @Transactional
    public void update(Client client) {
        logger.info("Обновление данных клиента: {}", client.getEmail());
        clientRepository.updateClient(
                client.getFirst_name(),
                client.getLast_name(),
                client.getPhone(),
                client.getPassword(),
                client.getId()
        );
    }

    @Transactional
    public void delete(Long id) {
        clientRepository.findById(id).ifPresent(client -> {
            logger.info("Удаление клиента и его корзин: {}", client.getEmail());
            cartRepository.deleteByClient(client);
        });

        clientRepository.deleteWithCarts(id);
    }

    public List<Client> findAll() {
        logger.debug("Получение списка всех клиентов");
        return clientRepository.findAll();
    }

    public Client getCurrentClient(HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        logger.debug("Получение клиента из сессии: {}", client != null ? client.getEmail() : "не авторизован");
        return client;
    }
}
