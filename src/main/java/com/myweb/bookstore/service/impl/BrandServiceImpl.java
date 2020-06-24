package com.myweb.bookstore.service.impl;

import com.myweb.bookstore.entity.Brand;
import com.myweb.bookstore.repository.BrandReponsitory;
import com.myweb.bookstore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandReponsitory brandReponsitory;

    @Override
    public Brand save(Brand brand) {
        return brandReponsitory.save(brand);
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandReponsitory.findById(id);
    }


    @Override
    public List<Brand> findAll() {
        return (List<Brand>) brandReponsitory.findAll();
    }

    @Override
    public long count() {
        return brandReponsitory.count();
    }

    @Override
    public void deleteById(Long id) {
        brandReponsitory.deleteById(id);
    }

}
