package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.BaseEntity;

import java.util.List;

public interface ServiceBase<T extends BaseEntity> {
    List<T> findAll();
    T findById(long id);
    T update(long id, T toUpdate);
    T create(T toCreate);
    void delete(long id);
}
