package com.laptrinhjava.salesmanager.controller;

import com.laptrinhjab.QuanLyBanHang.entity.*;
import com.laptrinhjava.salesmanager.entity.Order;
import com.laptrinhjava.salesmanager.repository.OrderDetailReponsitory;
import com.laptrinhjava.salesmanager.service.OrderService;
import com.laptrinhjava.salesmanager.service.ProductsService;
import com.laptrinhjava.salesmanager.service.UserService;
import com.laptrinhjava.salesmanager.entity.OrderDetail;
import com.laptrinhjava.salesmanager.entity.ResponseObject;
import com.laptrinhjava.salesmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "/api/bill")
@CrossOrigin(origins = "http://localhost:1212")
public class OrderController {
    @Autowired
    private ProductsService productsService;

    @Autowired
    private OrderService billService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailReponsitory orderDetailReponsitory;

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> createBill(@Valid @RequestBody Order order) {
//        Long sum = 0L;
//        Order bill = new Order();
//        Collection<Long> strProducts = billRequest.getProducts();
//        Iterator<Long> iterator = null; // khai báo một Iterator
//        Collection<Products> products = new ArrayList<>();
//
//
//        iterator = strProducts.iterator();
//        while (iterator.hasNext()) {
//            Products billProducts = productsService.findById(iterator.next())
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            products.add(billProducts);
//            sum += billProducts.getPrice();
//        }
//
//        bill.setListProductId(products);
//        bill.setTotalPrice(sum);

        for (OrderDetail orderDetail : order.getProducts()) {
            orderDetailReponsitory.save(orderDetail);
        }

        // get createdby tu tai khoan dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String foundUserName = authentication.getName(); // get userName from auth
        User user = userService.findByUserName(foundUserName); // tim user bang userName
        order.setCreatedBy(user.getFullName()); // Set CreatedBy tu fullname

//        billService.save(bill);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Them thanh cong",billService.save(order))
        );
    }

    @GetMapping("")
    List<Order> getAllBill() {
        return billService.findAll();
    }
}
