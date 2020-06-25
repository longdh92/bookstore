package com.myweb.sportthanhbinh.repository;

import com.myweb.sportthanhbinh.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReponsitory extends JpaRepository<Product,Long> {

}
