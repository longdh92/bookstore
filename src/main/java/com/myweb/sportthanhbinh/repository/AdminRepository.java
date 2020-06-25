package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findAdminByEmail(String email);
}
