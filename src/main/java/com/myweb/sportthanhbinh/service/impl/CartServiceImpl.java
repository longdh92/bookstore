package com.myweb.sportthanhbinh.service.impl;

import com.myweb.sportthanhbinh.entity.Cart;
import com.myweb.sportthanhbinh.repository.CartReponsitory;
import com.myweb.sportthanhbinh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartReponsitory cartReponsitory;

    @Override
    public Cart save(Cart entity) {
        return cartReponsitory.save(entity);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartReponsitory.findById(id);
    }

    @Override
    public List<Cart> findAll() {
        return (List<Cart>)cartReponsitory.findAll();
    }

    @Override
    public long count() {
        return cartReponsitory.count();
    }

    @Override
    public void deleteById(Long id) {
        cartReponsitory.deleteById(id);
    }

}
