package com.laptrinhjava.salesmanager.service;

import com.laptrinhjava.salesmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();
    User saveUser(User user);
    List<User> findAllByUerName(String userName);
    List<User> findAllByPassword(String password);
    Optional<User> findById(Long id);
    void delete(Long id);
    boolean existById(Long id);
    User findByUserName(String userName);
    Page<User> findUserWithPagination(int offset, int limit);
}
