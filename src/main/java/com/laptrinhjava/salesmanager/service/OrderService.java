package com.laptrinhjava.salesmanager.service;

import com.laptrinhjava.salesmanager.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order save(Order bill);
    List<Order> findAll();
}
