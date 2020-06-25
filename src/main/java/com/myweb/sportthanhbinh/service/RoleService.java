package com.myweb.sportthanhbinh.service;

import com.myweb.sportthanhbinh.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
}
