package kfu.itis.arseniy.controller;

import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.exception.EmailAlreadyExistException;
import kfu.itis.arseniy.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public String showRegistrationForm() {
        logger.info("Открыта форма регистрации.");
        return "registration";
    }

    @PostMapping
    public String registerClient(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String phone,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 Model model,
                                 HttpSession session) {

        boolean hasErrors = false;

        if (firstName.isEmpty()) {
            model.addAttribute("errorFirstName", "Имя не может быть пустым.");
            hasErrors = true;
        }

        if (lastName.isEmpty()) {
            model.addAttribute("errorLastName", "Фамилия не может быть пустой.");
            hasErrors = true;
        }

        if (email.isEmpty()) {
            model.addAttribute("errorEmail", "Email не может быть пустым.");
            hasErrors = true;
        }

        if (password.isEmpty()) {
            model.addAttribute("errorPassword", "Пароль не может быть пустым.");
            hasErrors = true;
        }

        if (hasErrors) {
            logger.warn("Ошибка валидации при регистрации клиента: {} {} {}", firstName, lastName, email);
            return "registration";
        }

        try {
            Client client = new Client(firstName, lastName, phone, email, password);
            clientService.register(client);
            Client savedClient = clientService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Клиент не найден после регистрации"));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(savedClient.getEmail(), password);

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("Клиент успешно зарегистрирован: {}", email);

            return "redirect:/clientPage";

        } catch (EmailAlreadyExistException e) {
            logger.warn("Ошибка регистрации: Email {} уже используется", email);
            model.addAttribute("errorEmail", e.getMessage());
            return "registration";
        } catch (Exception e) {
            logger.error("Ошибка регистрации клиента: {}", e.getMessage(), e);
            return "registration";
        }
    }
}
