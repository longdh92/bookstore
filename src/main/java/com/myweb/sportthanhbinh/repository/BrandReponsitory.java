package com.myweb.sportthanhbinh.repository;

import com.myweb.sportthanhbinh.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandReponsitory extends CrudRepository<Brand,Long> {


}
