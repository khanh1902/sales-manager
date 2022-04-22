package com.laptrinhjava.salesmanager.service;

import com.laptrinhjava.salesmanager.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> findAll();
}
