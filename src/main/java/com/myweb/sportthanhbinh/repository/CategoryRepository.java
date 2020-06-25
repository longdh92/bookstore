package com.myweb.sportthanhbinh.repository;

import com.myweb.sportthanhbinh.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findByNameLikeOrderByName(String name);

}
