package kfu.itis.arseniy.controller;

import kfu.itis.arseniy.entity.Client;
import kfu.itis.arseniy.service.ClientService;
import kfu.itis.arseniy.service.EmailCodeLoginService;
import kfu.itis.arseniy.service.EmailCodeService;
import kfu.itis.arseniy.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class EmailLoginController {

    private final ClientService clientService;
    private final EmailCodeLoginService emailCodeLoginService;

    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String showLoginPage() {
        return "email-login";  // шаблон, объединяющий оба способа
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/clientPage";
        } catch (Exception e) {
            model.addAttribute("error", "Неверный email или пароль.");
            return "email-login";
        }
    }

    @PostMapping("/send-code")
    public String sendCode(@RequestParam String email, Model model) {
        Optional<Client> clientOpt = clientService.findByEmail(email);
        if (!clientOpt.isPresent()) {
            model.addAttribute("error", "Пользователь не найден.");
            return "email-login";
        }

        emailCodeLoginService.sendLoginCode(email);

        model.addAttribute("email", email);
        return "verify-code";
    }

    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam String email,
                             @RequestParam String code,
                             HttpServletRequest request,
                             Model model) {

        Optional<Client> clientOpt = emailCodeLoginService.verifyCode(email, code);
        if (!clientOpt.isPresent()) {
            model.addAttribute("error", "Неверный или просроченный код.");
            model.addAttribute("email", email);
            return "verify-code";
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        return "redirect:/clientPage";
    }

}
