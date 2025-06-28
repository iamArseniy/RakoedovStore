package kfu.itis.arseniy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            logger.error("Ошибка с кодом: {}", statusCode);

            model.addAttribute("errorCode", statusCode);
            String message;

            switch (statusCode) {
                case 404:
                    message = "Страница не найдена.";
                    break;
                case 500:
                    message = "Внутренняя ошибка сервера.";
                    break;
                case 403:
                    message = "Доступ запрещён.";
                    break;
                default:
                    message = "Неизвестная ошибка.";
            }

            model.addAttribute("errorMessage", message);
        } else {
            logger.error("Ошибка без кода");
            model.addAttribute("errorCode", "Неизвестно");
            model.addAttribute("errorMessage", "Произошла ошибка.");
        }

        return "error";
    }
}
