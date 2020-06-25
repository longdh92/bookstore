package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailReponsitory extends JpaRepository<BillDetail,Long> {
}
