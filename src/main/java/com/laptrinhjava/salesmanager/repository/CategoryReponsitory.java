package com.laptrinhjava.salesmanager.repository;

import com.laptrinhjava.salesmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
}
