package com.laptrinhjava.salesmanager.repository;

import com.laptrinhjava.salesmanager.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsReponsitory extends JpaRepository<Products, Long> {
    List<Products> findByProductName(String productName);
    List<Products> findProductByCategoryId(Long categoryId);
}
