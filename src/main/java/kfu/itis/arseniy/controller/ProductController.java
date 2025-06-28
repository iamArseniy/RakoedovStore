package kfu.itis.arseniy.controller;

import javax.servlet.http.HttpSession;

import kfu.itis.arseniy.dto.ProductImageDto;
import kfu.itis.arseniy.entity.*;
import kfu.itis.arseniy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.util.Base64;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    private final ConversionService conversionService;

    @Autowired
    public ProductController(ProductService productService, ConversionService conversionService) {
        this.productService = productService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(name = "product_id", required = false) Long productId, Model model) {
        if (productId == null) {
            logger.warn("Попытка запроса страницы товара без product_id.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is null");
        }

        Product product = productService.findById(productId);

        if (product != null) {
            logger.info("Просмотр страницы товара: {}.", product.getName());
            ProductImageDto dto = conversionService.convert(product, ProductImageDto.class);

            model.addAttribute("product", dto);

            return "product";
        } else {
            logger.warn("Товар с ID {} не найден.", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found");
        }
    }
}
