package com.myweb.bookstore.service;


import com.myweb.bookstore.entity.Brand;

import java.util.Optional;

public interface BrandService {

    Brand save(Brand brand);

    Optional<Brand> findById(Long id);

    Iterable<Brand> findAll();

    long count();

    void deleteById(Long id);
}
