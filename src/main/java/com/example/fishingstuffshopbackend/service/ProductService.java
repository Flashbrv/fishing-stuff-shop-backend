package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.Product;

public interface ProductService extends ServiceBase<Product> {
    Product addToCategory(long productId, long categoryId);
    Product removeFromCategory(long productId, long categoryId);
}
