package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillReponsitory extends JpaRepository<Bill,Long> {
}
