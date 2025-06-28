package kfu.itis.arseniy.converter;

import kfu.itis.arseniy.dto.ProductImageDto;
import kfu.itis.arseniy.entity.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ProductToDtoConverter implements Converter<Product, ProductImageDto> {

    @Override
    public ProductImageDto convert(Product product) {
        String imageBase64 = null;
        if (product.getImage() != null) {
            imageBase64 = Base64.getEncoder().encodeToString(product.getImage());
        }

        return new ProductImageDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                imageBase64
        );
    }
}
