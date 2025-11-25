package com.example.category_crud_api.Repository;

import com.example.category_crud_api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // tìm danh mục có chứa chuỗi tên
    List<Category> findByCategoryNameContaining(String name);

    // giống trên nhưng có phân trang
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);

    // tìm đúng theo tên
    Optional<Category> findByCategoryName(String name);
}
