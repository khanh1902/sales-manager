package com.laptrinhjava.salesmanager.service.impl;

import com.laptrinhjava.salesmanager.entity.User;
import com.laptrinhjava.salesmanager.repository.UserReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserReponsitory userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    User user = userRepository.findUserByUserName(userName)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));

    return UserDetailsImpl.build(user);
  }

}
