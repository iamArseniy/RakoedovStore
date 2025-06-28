package kfu.itis.arseniy.service;

import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.repository.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Попытка загрузки пользователя по email: {}", email);

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("Пользователь не найден: {}", email);
                    return new UsernameNotFoundException("Client not found with email: " + email);
                });

        logger.info("Пользователь успешно найден: {}", email);

        return User.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .roles("USER")
                .build();
    }
}
