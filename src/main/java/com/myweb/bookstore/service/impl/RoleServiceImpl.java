package com.myweb.bookstore.service.impl;

import com.myweb.bookstore.entity.Role;
import com.myweb.bookstore.repository.RoleReponsitory;
import com.myweb.bookstore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleReponsitory roleReponsitory;

    @Override
    public Optional<Role> findById(Long id) {
        return roleReponsitory.findById(id);
    }
}
