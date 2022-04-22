package com.laptrinhjava.salesmanager.service.impl;

import com.laptrinhjava.salesmanager.entity.Category;
import com.laptrinhjava.salesmanager.repository.CategoryReponsitory;
import com.laptrinhjava.salesmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryReponsitory categoryReponsitory;

    @Override
    public List<Category> findAll() {
        return categoryReponsitory.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryReponsitory.save(category);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryReponsitory.findByName(name);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryReponsitory.findById(id);
    }

    @Override
    public boolean existById(Long id) {
        return categoryReponsitory.existsById(id);
    }

    @Override
    public void delete(Long id) {
        categoryReponsitory.deleteById(id);
    }
}
