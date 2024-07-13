package com.example.springexample.jpa.services;

import java.util.concurrent.ConcurrentHashMap;

public interface CRUDService<T> {

    T getById(long id);
    ConcurrentHashMap<Long, T> getAll();
    void create(T item);

    void update(T item);

    void deleteById(long id);
}
