package kfu.itis.arseniy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private String description;
    private double price;
}