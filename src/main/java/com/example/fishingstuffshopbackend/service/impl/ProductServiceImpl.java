package com.example.fishingstuffshopbackend.service.impl;

import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.exception.ProductNotFoundException;
import com.example.fishingstuffshopbackend.model.Category;
import com.example.fishingstuffshopbackend.model.Product;
import com.example.fishingstuffshopbackend.repository.CategoryRepository;
import com.example.fishingstuffshopbackend.repository.ProductRepository;
import com.example.fishingstuffshopbackend.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product update(long id, Product toUpdate) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setTitle(toUpdate.getTitle());
        product.setDescription(toUpdate.getDescription());
        product.setPrice(toUpdate.getPrice());
        product.setImage(toUpdate.getImage());
        return productRepository.save(product);
    }

    @Override
    public Product create(Product toCreate) {
        return productRepository.save(toCreate);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Product addProductToCategory(long productId, long categoryId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.addCategory(category);
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public List<Category> getProductCategories(long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return product.getCategories();
    }
}
