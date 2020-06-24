package com.myweb.bookstore.service.impl;

import com.myweb.bookstore.entity.Admin;
import com.myweb.bookstore.repository.AdminRepository;
import com.myweb.bookstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AdminServiceImpl implements AdminService {
     @Autowired
     private AdminRepository  adminRepository;

    @Override
    public void save(Admin entity) {
        adminRepository.save(entity);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Optional<Admin> findAdminByEmail(String email) {
        return
                adminRepository.findAdminByEmail(email);
    }
}
