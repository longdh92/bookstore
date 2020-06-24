package com.myweb.bookstore.repository;

import com.myweb.bookstore.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findByNameLikeOrderByName(String name);

}
