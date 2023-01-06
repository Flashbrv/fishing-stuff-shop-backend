package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.model.BaseEntity;

import java.util.List;

public interface ServiceBase<T extends BaseEntity> {
    List<T> findAll();
    T findById(Long id);
    T update(Long id, T toUpdate);
    T create(T toCreate);
    void delete(Long id);
}
