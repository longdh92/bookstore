package com.myweb.bookstore.service;

import com.myweb.bookstore.entity.Customer;

import java.util.Optional;

public interface CustomerService  {
    void save(Customer entity);
    Customer findByEmail(String email);
    Optional<Customer> findById(Long id);
}
