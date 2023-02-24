package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.Category;
import com.example.fishingstuffshopbackend.domain.Product;
import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.exception.ProductNotFoundException;
import com.example.fishingstuffshopbackend.repository.CategoryRepository;
import com.example.fishingstuffshopbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.fishingstuffshopbackend.utils.CheckParameterUtils.setIfNotNull;
import static com.example.fishingstuffshopbackend.utils.CheckParameterUtils.setIfNotNullOrBlank;

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
        setIfNotNullOrBlank(product::setTitle, toUpdate.getTitle());
        setIfNotNullOrBlank(product::setDescription, toUpdate.getDescription());
        setIfNotNullOrBlank(product::setImage, toUpdate.getImage());
        setIfNotNull(product::setPrice, toUpdate.getPrice());

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
