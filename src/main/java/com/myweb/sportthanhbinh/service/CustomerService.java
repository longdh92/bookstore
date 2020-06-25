package com.myweb.sportthanhbinh.service;

import com.myweb.sportthanhbinh.entity.Customer;

import java.util.Optional;

public interface CustomerService  {
    void save(Customer entity);
    Customer findByEmail(String email);
    Optional<Customer> findById(Long id);
}
