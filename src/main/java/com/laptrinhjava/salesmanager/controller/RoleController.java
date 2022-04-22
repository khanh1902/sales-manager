package com.laptrinhjava.salesmanager.controller;

import com.laptrinhjava.salesmanager.entity.Role;
import com.laptrinhjava.salesmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    List<Role> getAllRole() {
        return roleService.findAll();
    }
}
