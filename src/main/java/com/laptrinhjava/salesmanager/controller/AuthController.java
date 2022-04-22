package com.laptrinhjava.salesmanager.controller;

import com.laptrinhjava.salesmanager.entity.ERole;
import com.laptrinhjava.salesmanager.entity.ResponseObject;
import com.laptrinhjava.salesmanager.entity.Role;
import com.laptrinhjava.salesmanager.entity.User;
import com.laptrinhjava.salesmanager.payload.request.ForgotPasswordRequest;
import com.laptrinhjava.salesmanager.payload.request.LoginRequest;
import com.laptrinhjava.salesmanager.payload.request.SignupRequest;
import com.laptrinhjava.salesmanager.payload.response.JwtResponse;
import com.laptrinhjava.salesmanager.payload.response.MessageResponse;
import com.laptrinhjava.salesmanager.repository.RoleReponsitory;
import com.laptrinhjava.salesmanager.repository.UserReponsitory;
import com.laptrinhjava.salesmanager.sercurity.jwt.JwtUtils;
import com.laptrinhjava.salesmanager.service.UserService;
import com.laptrinhjava.salesmanager.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController

@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserReponsitory userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    RoleReponsitory roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @CrossOrigin(origins = "http://localhost:1212")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        List<User> foundUserName = userService.findAllByUerName(loginRequest.getUserName());
        Boolean foundPassword = null;
        if (!foundUserName.isEmpty()) {
            User user = userService.findByUserName(loginRequest.getUserName());
            foundPassword = encoder.matches(loginRequest.getPassword(), user.getPassword());
        }
        if (foundUserName.isEmpty() || foundPassword == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Ten dang nhap hoac mat khau khong dung", "")
            );
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));

    }

    @CrossOrigin(origins = "http://localhost:1212")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Ten dang nhap da ton tai"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUserName(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @CrossOrigin(origins = "http://localhost:1212")
    @PostMapping("/forgot-password")
    public ResponseEntity<?> registerUser(@RequestParam(name = "username") String userName, @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        User foundUser = userRepository.findByUserName(userName);
        if (foundUser == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username not exists!"));
        } else {
            if (forgotPasswordRequest.getNewPassword().equals(forgotPasswordRequest.getConfirmPassword())) {
                foundUser.setPassword(encoder.encode(forgotPasswordRequest.getNewPassword()));
                userRepository.save(foundUser);
                return ResponseEntity.ok("Update password successfully!");
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Confirm password does not match!"));
            }
        }
    }
}
