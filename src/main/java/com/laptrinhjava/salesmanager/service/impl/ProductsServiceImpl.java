package com.laptrinhjava.salesmanager.service.impl;

import com.laptrinhjava.salesmanager.entity.Products;
import com.laptrinhjava.salesmanager.repository.ProductsReponsitory;
import com.laptrinhjava.salesmanager.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsReponsitory productsReponsitory;

    @Override
    public Products save(Products product) {
        return productsReponsitory.save(product);
    }

    @Override
    public List<Products> findAll() {
        return productsReponsitory.findAll();
    }

    @Override
    public Optional<Products> findById(Long id) {
        return productsReponsitory.findById(id);
    }

    @Override
    public List<Products> findByProductName(String productName) {
        return productsReponsitory.findByProductName(productName);
    }

    @Override
    public void delete(Long id) {
        productsReponsitory.deleteById(id);
    }

    @Override
    public boolean existsByID(Long id) {
        return productsReponsitory.existsById(id);
    }

    @Override
    public Page<Products> findProductWithPagination(int offset, int limit) {
        return productsReponsitory.findAll(PageRequest.of(offset, limit));
    }

    @Override
    public List<Products> findProductByCategoryId(Long categoryId) {
        return productsReponsitory.findProductByCategoryId(categoryId);
    }
}
