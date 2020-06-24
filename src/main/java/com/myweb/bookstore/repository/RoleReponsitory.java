package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleReponsitory  extends JpaRepository<Role,Long> {
}
