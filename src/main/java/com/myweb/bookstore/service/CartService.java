package com.myweb.bookstore.service;

import com.myweb.bookstore.entity.Cart;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart save(Cart entity);

    Optional<Cart> findById(Long id);

    List<Cart> findAll();

    long count();

    void deleteById(Long id);
}
