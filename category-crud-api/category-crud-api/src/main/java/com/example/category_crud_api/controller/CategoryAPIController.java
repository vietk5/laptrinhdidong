package com.example.category_crud_api.controller;

import com.example.category_crud_api.entity.Category;
import com.example.category_crud_api.model.Response;
import com.example.category_crud_api.service.ICategoryService;
import com.example.category_crud_api.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    IStorageService storageService;

    // GET all
    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<>(
                new Response(true, "Thành công", categoryService.findAll()),
                HttpStatus.OK
        );
    }

    // GET 1 category theo id (truyền id qua form-data hoặc param)
    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(
                    new Response(true, "Thành công", category.get()),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new Response(false, "Không tìm thấy Category", null),
                HttpStatus.NOT_FOUND
        );
    }

    // ADD category + upload icon
    @PostMapping(path = "/addCategory")
    public ResponseEntity<?> addCategory(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("icon") MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Category đã tồn tại");
        }

        Category category = new Category();

        if (!icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            category.setIcon(storageService.getSorageFilename(icon, uuString));
            storageService.store(icon, category.getIcon());
        }

        category.setCategoryName(categoryName);
        categoryService.save(category);

        return new ResponseEntity<>(
                new Response(true, "Thêm thành công", category),
                HttpStatus.OK
        );
    }

    // UPDATE
    @PutMapping(path = "/updateCategory")
    public ResponseEntity<?> updateCategory(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("icon") MultipartFile icon) {

        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(
                    new Response(false, "Không tìm thấy Category", null),
                    HttpStatus.BAD_REQUEST
            );
        }

        Category category = optCategory.get();

        if (!icon.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();
            category.setIcon(storageService.getSorageFilename(icon, uuString));
            storageService.store(icon, category.getIcon());
        }
        category.setCategoryName(categoryName);
        categoryService.save(category);

        return new ResponseEntity<>(
                new Response(true, "Cập nhật thành công", category),
                HttpStatus.OK
        );
    }

    // DELETE
    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(
                    new Response(false, "Không tìm thấy Category", null),
                    HttpStatus.BAD_REQUEST
            );
        }
        categoryService.delete(optCategory.get());
        return new ResponseEntity<>(
                new Response(true, "Xóa thành công", optCategory.get()),
                HttpStatus.OK
        );
    }
}
