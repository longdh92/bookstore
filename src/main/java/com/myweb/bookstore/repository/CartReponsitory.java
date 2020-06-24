package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartReponsitory extends CrudRepository<Cart,Long> {

    @Query("FROM Cart c WHERE c.customer.id=:customerid")
    Cart findByCustomer(Long customerid);

    @Query("select sum(cd.quantity*cd.product.price) from Cart c ,CartDetail cd where c.customer.id=:customerid and c.id=cd.cart.id ")
    Double total(Long customerid);


}
