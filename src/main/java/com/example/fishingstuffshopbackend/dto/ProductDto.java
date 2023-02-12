package com.example.fishingstuffshopbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private long id;
    private String title;
    private String description;
    private String image;
    private BigDecimal price;
    private Set<CategoryDto> categories;
}
