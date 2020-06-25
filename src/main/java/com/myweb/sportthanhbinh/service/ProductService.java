package com.myweb.sportthanhbinh.service;

import com.myweb.sportthanhbinh.entity.Product;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {

    Product save(Product entity);

    Optional<Product> findById(Long id);

    boolean existsById(Long id);

    Iterable<Product> findAll();

    long count();

    void deleteById(Long id);

    Page<Product> findPaginated(int pageNo,int pageSize);
}
