package com.example.fishingstuffshopbackend.service.impl;

import com.example.fishingstuffshopbackend.exception.SuchCategoryExist;
import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.model.Category;
import com.example.fishingstuffshopbackend.repository.CategoryRepository;
import com.example.fishingstuffshopbackend.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.fishingstuffshopbackend.service.CheckParameterUtils.requireNonNullAndGreaterThanZero;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        log.debug("Get all ProductTypeEntities");
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(long id) {
        log.debug("Get ProductTypeEntity with id=" + id);
        return categoryRepository
                .findById(requireNonNullAndGreaterThanZero("id", id))
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category update(long id, Category toUpdate) {
        log.debug("Update category with id=" + id);
        Category fromDB = categoryRepository
                .findById(requireNonNullAndGreaterThanZero("id", id))
                .orElseThrow(() -> new CategoryNotFoundException(id));

        fromDB.setTitle(toUpdate.getTitle());

        log.debug("Update category " + fromDB);
        return categoryRepository.save(fromDB);
    }

    @Override
    public Category create(Category toCreate) {
        log.info("Create category: " + toCreate);

        try {
            return categoryRepository.save(toCreate);
        } catch (DataIntegrityViolationException ex) {
            throw new SuchCategoryExist(toCreate.getTitle());
        }
    }

    @Override
    public void delete(long id) {
        log.info("Delete category with id=" + id);
        try {
            categoryRepository.deleteById(requireNonNullAndGreaterThanZero("id", id));
            log.info("Category with id=" + id + " deleted");
        } catch (EmptyResultDataAccessException ex) {
            log.debug("Try to delete category with id=" + id + ", which doesn't exist");
        }
    }
}
