package com.laptrinhjava.salesmanager.controller;

import com.laptrinhjava.salesmanager.entity.Products;
import com.laptrinhjava.salesmanager.entity.ResponseObject;
import com.laptrinhjava.salesmanager.entity.User;
import com.laptrinhjava.salesmanager.service.ProductsService;
import com.laptrinhjava.salesmanager.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/product")
@CrossOrigin(origins = "http://localhost:1212")
public class ProductsController {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    Page<Products> getProductWithPagination(@RequestParam(name = "offset") int offset,
                                            @RequestParam(name = "limit") int limit){
        return productsService.findProductWithPagination(offset, limit);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Products> foundId = productsService.findById(id);
        if(foundId.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseObject("OK", "Tim thanh cong", productsService.findById(id))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Khong tim thay san pham co ID la "+id, "")
        );
    }

    // Tim san pham theo loai san pham
    @GetMapping("/{categoryid}")
    ResponseEntity<ResponseObject> findProductByCategoryId(@PathVariable Long categoryid){
        List<Products> foundProduct = productsService.findProductByCategoryId(categoryid);
        if(!foundProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Tim thanh cong", foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Khong tim thay nguoi dung " + categoryid, "")
            );
        }
    }

    // Them san pham
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody @NotNull Products product){
        List<Products> foundProduct = productsService.findByProductName(product.getProductName().trim());
        if(foundProduct.size()>0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
              new ResponseObject("failed", "Ten san pham da ton tai","")
            );
        }
        // get createdby tu tai khoan dang nhap
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String foundUserName = authentication.getName(); // get userName from auth
        User user = userService.findByUserName(foundUserName); // tim user bang userName
        product.setCreatedBy(user.getFullName()); // Set CreatedBy tu fullname

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Them thanh cong",productsService.save(product))
        );
    }

    // Cap nhat san pham
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Products newProducts, @PathVariable Long id){
        Products updateProduct = productsService.findById(id)
                .map(products -> {
                    products.setProductName(newProducts.getProductName());
                    products.setPrice(newProducts.getPrice());
                    products.setQuantity(newProducts.getQuantity());
                    products.setCategoryId(newProducts.getCategoryId());
                    return productsService.save(products);
                }).orElseGet( ()-> {
                     newProducts.setId(id);
                     return productsService.save(newProducts);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Cap nhat thanh cong", updateProduct)
        );
    }

    // Xoa san pham
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        if (productsService.existsByID(id)) {
            productsService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Xoa thanh cong", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("ok", "Khong tim thay san pham de xoa", "")
        );
    }
}
