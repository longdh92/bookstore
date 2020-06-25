package com.myweb.sportthanhbinh.service.impl;

import com.myweb.sportthanhbinh.entity.Customer;
import com.myweb.sportthanhbinh.repository.CustomerReponsitory;
import com.myweb.sportthanhbinh.repository.RoleReponsitory;
import com.myweb.sportthanhbinh.service.CustomerService;
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
