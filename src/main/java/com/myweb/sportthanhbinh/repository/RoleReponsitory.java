package com.myweb.sportthanhbinh.repository;

import com.myweb.sportthanhbinh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReponsitory  extends JpaRepository<Role,Long> {
}
