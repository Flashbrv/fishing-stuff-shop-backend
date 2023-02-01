package com.example.fishingstuffshopbackend.service.impl;

import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.exception.ProductNotFoundException;
import com.example.fishingstuffshopbackend.model.Category;
import com.example.fishingstuffshopbackend.model.Product;
import com.example.fishingstuffshopbackend.repository.CategoryRepository;
import com.example.fishingstuffshopbackend.repository.ProductRepository;
import com.example.fishingstuffshopbackend.service.ProductService;
import org.springframework.stereotype.Service;

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

    @Override
    public Product addToCategory(long productId, long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.addCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product removeFromCategory(long productId, long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.removeCategory(category);
        return productRepository.save(product);
    }
}
