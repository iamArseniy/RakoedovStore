package kfu.itis.arseniy.controller;

import kfu.itis.arseniy.entity.Product;
import kfu.itis.arseniy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Controller
public class MainPageController {

    private static final Logger logger = LoggerFactory.getLogger(MainPageController.class);

    private final ProductService productService;

    @Autowired
    public MainPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/mainpage")
    public String getMainPage(Model model) {
        List<Product> products = productService.findAllWithImages();
        model.addAttribute("products", products);
        logger.info("Главная страница загружена с {} товарами.", products.size());
        return "mainpage";
    }
}
