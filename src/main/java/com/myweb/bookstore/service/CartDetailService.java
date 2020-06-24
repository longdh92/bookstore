package com.myweb.bookstore.service;

import com.myweb.bookstore.entity.CartDetail;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    CartDetail save(CartDetail entity);

    Optional<CartDetail> findById(Long id);

    List<CartDetail> findAll();

    long count();

    void deleteById(Long id);
}
