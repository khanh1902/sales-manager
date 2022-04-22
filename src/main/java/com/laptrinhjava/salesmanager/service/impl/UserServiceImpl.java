package com.laptrinhjava.salesmanager.service.impl;

import com.laptrinhjava.salesmanager.entity.User;
import com.laptrinhjava.salesmanager.repository.UserReponsitory;
import com.laptrinhjava.salesmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserReponsitory userReponsitory;

    @Override
    public List<User> findAll() {
        return userReponsitory.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userReponsitory.save(user);
    }

    @Override
    public List<User> findAllByUerName(String userName) {
        return userReponsitory.findAllByUserName(userName);
    }

    @Override
    public List<User> findAllByPassword(String password) {
        return userReponsitory.findAllByPassword(password);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userReponsitory.findById(id);
    }

    @Override
    public void delete(Long id) {
        userReponsitory.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return userReponsitory.existsById(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userReponsitory.findByUserName(userName);
    }

    @Override
    public Page<User> findUserWithPagination(int offset, int limit) {
        return userReponsitory.findAll(PageRequest.of(offset, limit));
    }
}
