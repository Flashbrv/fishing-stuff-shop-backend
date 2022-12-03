package com.example.fishingstuffshopbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ProductDto implements Serializable {
    private static final long serialVersionUID = -6712610903293163848L;
    private Integer id;
    private String name;
}
