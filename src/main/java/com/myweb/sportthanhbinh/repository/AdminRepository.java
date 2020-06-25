package com.myweb.sportthanhbinh.repository;

import com.myweb.sportthanhbinh.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findAdminByEmail(String email);

    @Query("select a from  Admin a where a.role > :role")
    List<Admin> findAllByRole(int role);
}
