package com.laptrinhjava.salesmanager.service;

import com.laptrinhjava.salesmanager.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    List<Category> findAll();
    Category saveCategory(Category category);
    List<Category> findByName(String name);
    Optional<Category> findById(Long id);
    boolean existById(Long id);
    void delete(Long id);
}
