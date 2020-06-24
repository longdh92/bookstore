package com.myweb.bookstore.service.impl;

import com.myweb.bookstore.entity.Cart;
import com.myweb.bookstore.entity.Customer;
import com.myweb.bookstore.entity.Role;
import com.myweb.bookstore.repository.CustomerReponsitory;
import com.myweb.bookstore.repository.RoleReponsitory;
import com.myweb.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerReponsitory customerReponsitory;

    @Autowired
    private RoleReponsitory roleReponsitory;
    @Override
    public void save(Customer entity) {
        customerReponsitory.save(entity);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerReponsitory.findByEmail(email);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerReponsitory.findById(id);
    }
}
