package kfu.itis.arseniy.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        logger.error("Произошло исключение: ", ex);
        model.addAttribute("errorCode", "500");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
