package com.example.walet.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T, V> {
    void create(T t);
    Optional<T> findById(V v);
    List<V> findAllId();
    List<T> findAll();
    void update(T t);
}
