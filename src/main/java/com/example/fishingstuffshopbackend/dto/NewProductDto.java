package com.example.fishingstuffshopbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDto implements Serializable {
    private String title;
    private String description;
    private String image;
    private BigDecimal price;
}
