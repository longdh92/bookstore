package com.myweb.sportthanhbinh.service.impl;

import com.myweb.sportthanhbinh.entity.Product;
import com.myweb.sportthanhbinh.repository.CategoryRepository;
import com.myweb.sportthanhbinh.repository.ProductReponsitory;
import com.myweb.sportthanhbinh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductReponsitory productReponsitory;

    @Autowired
    private CategoryRepository caterepository;

    @Override
    public Product save(Product entity) {
        return productReponsitory.save(entity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productReponsitory.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productReponsitory.existsById(id);
    }

    @Override
    public List<Product> findAll() {

        return (List<Product>) productReponsitory.findAll();
    }

    @Override
    public long count() {
        return productReponsitory.count();
    }

    @Override
    public void deleteById(Long id) {
        productReponsitory.deleteById(id);
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1 ,pageSize);
        return productReponsitory.findAll(pageable);
    }


}
