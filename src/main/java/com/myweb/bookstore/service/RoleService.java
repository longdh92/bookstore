package com.myweb.bookstore.service;

import com.myweb.bookstore.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
}
