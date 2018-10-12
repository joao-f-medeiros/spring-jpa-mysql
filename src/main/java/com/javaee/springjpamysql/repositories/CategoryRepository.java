package com.javaee.springjpamysql.repositories;

import org.springframework.data.repository.CrudRepository;

import com.javaee.springjpamysql.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

}
