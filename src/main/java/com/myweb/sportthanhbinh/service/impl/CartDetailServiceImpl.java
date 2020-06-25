package com.myweb.sportthanhbinh.service.impl;

import com.myweb.sportthanhbinh.entity.CartDetail;
import com.myweb.sportthanhbinh.repository.CartDetailReponsitory;
import com.myweb.sportthanhbinh.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    private CartDetailReponsitory cartDetailReponsitory;

    @Override
    public CartDetail save(CartDetail entity) {
        return cartDetailReponsitory.save(entity);
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return cartDetailReponsitory.findById(id);
    }

    @Override
    public List<CartDetail> findAll() {
        return (List<CartDetail>)cartDetailReponsitory.findAll();
    }

    @Override
    public long count() {
        return cartDetailReponsitory.count();
    }

    @Override
    public void deleteById(Long id) {
        cartDetailReponsitory.deleteById(id);
    }
}
