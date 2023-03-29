package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.exception.SuchCategoryExistException;
import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.domain.Category;
import com.example.fishingstuffshopbackend.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.fishingstuffshopbackend.utils.CheckParameterUtils.*;

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
                .findById(requireGreaterThanZero("Category id", id))
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category create(Category toCreate) {
        log.info("Create category: " + toCreate);
        requireNonNull("New category", toCreate);
        requireNonNullAndNonBlank("Category title", toCreate.getTitle());
        try {
            return categoryRepository.save(toCreate);
        } catch (DataIntegrityViolationException ex) {
            throw new SuchCategoryExistException(toCreate.getTitle());
        }
    }

    @Override
    public Category update(long id, Category toUpdate) {
        log.debug("Update category with id=" + id);
        Category fromDB = categoryRepository
                .findById(requireGreaterThanZero("Category id", id))
                .orElseThrow(() -> new CategoryNotFoundException(id));

        requireNonNull("Category to update", toUpdate);
        setIfNotNullOrBlank(fromDB::setTitle, toUpdate.getTitle());

        log.debug("Update category " + fromDB);
        return categoryRepository.save(fromDB);
    }

    @Override
    public void delete(long id) {
        log.info("Delete category with id=" + id);
        try {
            categoryRepository.deleteById(requireGreaterThanZero("Category id", id));
            log.info("Category with id=" + id + " deleted");
        } catch (EmptyResultDataAccessException ex) {
            log.debug("Try to delete category with id=" + id + ", which doesn't exist");
        }
    }
}
