package com.myweb.sportthanhbinh.service.impl;

import com.myweb.sportthanhbinh.entity.Role;
import com.myweb.sportthanhbinh.repository.RoleReponsitory;
import com.myweb.sportthanhbinh.service.RoleService;
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
