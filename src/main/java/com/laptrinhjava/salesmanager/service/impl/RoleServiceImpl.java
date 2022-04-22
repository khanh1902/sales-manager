package com.laptrinhjava.salesmanager.service.impl;

import com.laptrinhjava.salesmanager.entity.Role;
import com.laptrinhjava.salesmanager.repository.RoleReponsitory;
import com.laptrinhjava.salesmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleReponsitory roleReponsitory;

    @Override
    public List<Role> findAll() {
        return roleReponsitory.findAll();
    }
}
