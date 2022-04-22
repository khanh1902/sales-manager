package com.laptrinhjava.salesmanager.controller;

import com.laptrinhjava.salesmanager.entity.Category;
import com.laptrinhjava.salesmanager.entity.ResponseObject;
import com.laptrinhjava.salesmanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/category")
@CrossOrigin(origins = "http://localhost:1212")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    List<Category> getAllCategories(){
        return categoryService.findAll();
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertCategory(@RequestBody Category category) {
        List<Category> foundCategory = categoryService.findByName(category.getName().trim());
        if(foundCategory.size() > 0){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","Loại sản phẩm đã tồn tại", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok","Thêm thành công", categoryService.saveCategory(category))
        );
    }

    // Cap nhat the loai
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updatCategory(@RequestBody Category newCategory, @PathVariable Long id) {
        Category updateCategory = categoryService.findById(id).map(
                category -> {
                    category.setCode(newCategory.getCode());
                    category.setName(newCategory.getName());
                    return categoryService.saveCategory(category);
                }).orElseGet(() -> {
            newCategory.setId(id);
            return categoryService.saveCategory(newCategory);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Cập nhật thành công", updateCategory)
        );
    }

    // Xoa the loai
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
        if(categoryService.existById(id)) {
            categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Xóa thành công","")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("OK", "Xoá không thành công","")
        );
    }
}
