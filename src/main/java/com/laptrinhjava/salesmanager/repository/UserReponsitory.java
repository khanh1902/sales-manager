package com.laptrinhjava.salesmanager.repository;

import com.laptrinhjava.salesmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReponsitory extends JpaRepository<User, Long> {
    List<User> findAllByUserName(String userName);
    Boolean existsByUserName(String userName);
    Optional<User> findUserByUserName(String userName);
    User findByUserName(String userName);
    List<User> findAllByPassword(String password);
}
