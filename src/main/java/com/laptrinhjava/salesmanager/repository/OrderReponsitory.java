package com.laptrinhjava.salesmanager.repository;

import com.laptrinhjava.salesmanager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReponsitory extends JpaRepository<Order, Long> {

}
