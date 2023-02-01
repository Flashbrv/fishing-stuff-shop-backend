package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.model.Product;

public interface ProductService extends ServiceBase<Product> {
    Product addToCategory(long productId, long categoryId);
    Product removeFromCategory(long productId, long categoryId);
}
