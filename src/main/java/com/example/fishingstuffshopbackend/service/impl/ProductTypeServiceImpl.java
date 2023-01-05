package com.example.fishingstuffshopbackend.service.impl;

import com.example.fishingstuffshopbackend.dto.ProductTypeDto;
import com.example.fishingstuffshopbackend.exception.ProductTypeEntityWithSuchNameExist;
import com.example.fishingstuffshopbackend.exception.ProductTypeNotFoundException;
import com.example.fishingstuffshopbackend.mapper.ProductTypeMapper;
import com.example.fishingstuffshopbackend.model.ProductTypeEntity;
import com.example.fishingstuffshopbackend.repository.ProductTypeRepository;
import com.example.fishingstuffshopbackend.service.CheckParameterUtils;
import com.example.fishingstuffshopbackend.service.ProductTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.fishingstuffshopbackend.service.CheckParameterUtils.*;

@Service
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository typeRepository;
    private final ProductTypeMapper typeMapper;


    public ProductTypeServiceImpl(ProductTypeRepository typeRepository,
                                  ProductTypeMapper mapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = mapper;
    }

    @Override
    public List<ProductTypeDto> findAll() {
        log.debug("Get all ProductTypeEntities");
        return typeRepository.findAll()
                .stream()
                .map(typeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductTypeDto findById(Long id) {
        log.debug("Get ProductTypeEntity with id=" + id);
        return typeMapper.toDto(typeRepository
                .findById(requireNonNullAndGreaterThanZero("id", id))
                .orElseThrow(() -> new ProductTypeNotFoundException(id)));
    }

    @Override
    public ProductTypeDto update(Long id, String newName) {
        log.debug("Try to update ProductTypeEntity with id=" + id);
        ProductTypeEntity type = typeRepository
                .findById(requireNonNullAndGreaterThanZero("id", id))
                .orElseThrow(() -> new ProductTypeNotFoundException(id));

        type.setName(requireNonNullAndNonBlank("newName", newName));

        log.debug("Update ProductTypeEntity " + type);
        return typeMapper.toDto(typeRepository.save(type));
    }

    @Override
    public ProductTypeDto create(String name) {
        ProductTypeEntity type = new ProductTypeEntity();
        type.setName(requireNonNullAndNonBlank("name", name));
        log.info("Create ProductTypeEntity with name: " + name);

        try {
            return typeMapper.toDto(typeRepository.save(type));
        } catch (DataIntegrityViolationException ex) {
            throw new ProductTypeEntityWithSuchNameExist(name);
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete ProductTypeEntity with id=" + id);
        try {
            typeRepository.deleteById(requireNonNullAndGreaterThanZero("id", id));
            log.info("ProductTypeEntity with id=" + id + " deleted");
        } catch (EmptyResultDataAccessException ex) {
            log.debug("Try to delete ProductTypeEntity with id=" + id + ", which doesn't exist");
        }
    }
}
