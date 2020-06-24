package com.myweb.bookstore.service.impl;

import com.myweb.bookstore.entity.Cart;
import com.myweb.bookstore.entity.CartDetail;
import com.myweb.bookstore.repository.CartDetailReponsitory;
import com.myweb.bookstore.service.CartDetailService;
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
