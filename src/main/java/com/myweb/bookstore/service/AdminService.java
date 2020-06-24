package com.myweb.bookstore.service;

import com.myweb.bookstore.entity.Admin;
import com.myweb.bookstore.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void save(Admin entity);

    Optional<Admin> findById(Long id);

    List<Admin> findAll();

    void deleteById(Long id);

    Optional<Admin> findAdminByEmail(String email);


}
