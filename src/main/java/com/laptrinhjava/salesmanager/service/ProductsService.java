package com.laptrinhjava.salesmanager.service;

import com.laptrinhjava.salesmanager.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductsService {
    Products save(Products product);
    List<Products> findAll();
    Optional<Products> findById(Long id);
    List<Products> findByProductName(String productName);
    void delete(Long id);
    boolean existsByID(Long id);
    Page<Products> findProductWithPagination(int offset, int limit);
    List<Products> findProductByCategoryId(Long categoryId);
}
