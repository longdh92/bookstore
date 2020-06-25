package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AdminReponsitory extends JpaRepository<Admin,Long> {

    Optional<Admin> findAdminByEmail(String email);
}
