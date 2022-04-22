package com.laptrinhjava.salesmanager.service.impl;

import com.laptrinhjava.salesmanager.entity.Order;
import com.laptrinhjava.salesmanager.repository.OrderReponsitory;
import com.laptrinhjava.salesmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderReponsitory billReponsitory;

    @Override
    public Order save(Order bill) {
        return billReponsitory.save(bill);
    }

    @Override
    public List<Order> findAll() {
        return billReponsitory.findAll();
    }
}
