package com.myweb.sportthanhbinh.service;


import com.myweb.sportthanhbinh.entity.Brand;

import java.util.Optional;

public interface BrandService {

    Brand save(Brand brand);

    Optional<Brand> findById(Long id);

    Iterable<Brand> findAll();

    long count();

    void deleteById(Long id);
}
