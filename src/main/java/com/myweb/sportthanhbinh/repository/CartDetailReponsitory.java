package com.myweb.sportthanhbinh.repository;

import com.myweb.sportthanhbinh.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartDetailReponsitory extends JpaRepository<CartDetail,Long> {

    @Query("select d from CartDetail d,Cart c where c.customer.id=:id and c.id=d.cart.id")
    List<CartDetail> findByCustomer(Long id);

}
