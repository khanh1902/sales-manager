package com.laptrinhjava.salesmanager.controller;

import com.laptrinhjava.salesmanager.entity.ERole;
import com.laptrinhjava.salesmanager.entity.ResponseObject;
import com.laptrinhjava.salesmanager.entity.Role;
import com.laptrinhjava.salesmanager.entity.User;
import com.laptrinhjava.salesmanager.repository.RoleReponsitory;
import com.laptrinhjava.salesmanager.repository.UserReponsitory;
import com.laptrinhjava.salesmanager.sercurity.jwt.JwtUtils;
import com.laptrinhjava.salesmanager.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/user")
@CrossOrigin(originPatterns = "http://localhost:1212")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserReponsitory userRepository;

    @Autowired
    RoleReponsitory roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    ResponseEntity<ResponseObject> findByUserName(@PathVariable String username) {
        List<User> foundUser = userService.findAllByUerName(username);
        if (!foundUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Tim thanh cong", foundUser)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Khong tim thay nguoi dung " + username, "")
            );
        }
    }

    //Pagination By Id
    @GetMapping("")
    Page<User> getUserWithPagination(@RequestParam(name = "offset") int offset,
                                     @RequestParam(name = "limit") int limit) {
        return userService.findUserWithPagination(offset, limit);
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody @NotNull User user) {
        // Check username da ton tai chua
//        List<User> foundUser = userService.findAllByUerName(user.getUserName().trim()); //trim de xoa space dau va cuoi chuoi
//        if(foundUser.size() > 0) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseObject("failed","Ten dang nhap da ton tai. Vui long nhap lai ten khac", "")
//            );
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(
//          new ResponseObject("ok","Them thanh cong", userService.saveUser(user))
//        );
        if (userRepository.existsByUserName(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Tên đăng nhập đã tồn tại. Vui lòng nhập lại tên khác", "")
            );
        }
        // Create new user
        User createUser = new User(user.getUserName(),
                encoder.encode(user.getPassword()), user.getFullName(),
                user.getAddress(), user.getBirthday(), user.getSdt());

        // Get Role
        Set<String> strRoles = Collections.singleton("user");
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }
        createUser.setRoles(roles);

        // save user
//        userRepository.save(createUser);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok", "Thêm thành công", "")
//        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Thêm thành công", userRepository.save(createUser))
        );
    }

    // Cap nhat nhan vien
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody User newUser, @PathVariable Long id) {
        User foundUser = userService.findByUserName(newUser.getUserName());
        User updateUser = userService.findById(id)
                .map(user -> {
                    user.setAddress(newUser.getAddress());
                    user.setUserName(newUser.getUserName());
                    if(newUser.getPassword() != null) {
                        user.setPassword(encoder.encode(newUser.getPassword()));
                    }
                    else {
                        user.setPassword(foundUser.getPassword());
                    }
                    user.setFullName(newUser.getFullName());
                    user.setAddress(newUser.getAddress());
                    user.setBirthday(newUser.getBirthday());
                    user.setSdt(newUser.getSdt());
                    return userService.saveUser(user);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userService.saveUser(newUser);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Cập nhật thành công", updateUser)
        );
    }

    // Xoa nhan vien
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        if (userService.existById(id)) {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Xoa thanh cong", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("ok", "Khong tim thay nhan vien de xoa", "")
        );
    }
}
