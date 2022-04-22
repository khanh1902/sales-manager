package com.laptrinhjava.salesmanager.repository;

import com.laptrinhjava.salesmanager.entity.ERole;
import com.laptrinhjava.salesmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleReponsitory extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
