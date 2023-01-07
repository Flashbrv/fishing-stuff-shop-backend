package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.model.Category;
import com.example.fishingstuffshopbackend.model.Product;

import java.util.List;

public interface ProductService extends ServiceBase<Product> {
    Product addProductToCategory(long productId, long categoryId);
    List<Category> getProductCategories(long productId);
}
