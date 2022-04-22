package com.laptrinhjava.salesmanager.repository;

import com.laptrinhjava.salesmanager.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailReponsitory extends JpaRepository<OrderDetail, Long> {
}
