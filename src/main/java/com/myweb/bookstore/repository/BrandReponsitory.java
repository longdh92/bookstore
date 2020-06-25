package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandReponsitory extends CrudRepository<Brand,Long> {


}
