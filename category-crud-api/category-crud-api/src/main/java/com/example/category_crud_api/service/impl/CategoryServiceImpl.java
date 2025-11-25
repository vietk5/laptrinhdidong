package com.example.category_crud_api.service.impl;

import com.example.category_crud_api.entity.Category;
import com.example.category_crud_api.Repository.CategoryRepository;
import com.example.category_crud_api.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // ====== HÀM SAVE có xử lý icon (thêm + sửa) ======
    @Override
    public <S extends Category> S save(S entity) {
        // THÊM MỚI
        if (entity.getCategoryId() == null) {
            return categoryRepository.save(entity);
        }

        // CẬP NHẬT
        Optional<Category> opt = findById(entity.getCategoryId());
        if (opt.isPresent()) {
            Category old = opt.get();

            // Nếu icon mới null hoặc rỗng -> giữ icon cũ
            if (entity.getIcon() == null || entity.getIcon().isBlank()) {
                entity.setIcon(old.getIcon());
            }
            // else: đã set icon mới trong controller rồi, cứ để nguyên
        }

        return categoryRepository.save(entity);
    }

    // ====== CÁC HÀM CÒN LẠI CHỦ YẾU GỌI THẲNG REPOSITORY ======

    @Override
    public void delete(Category entity) {
        categoryRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return categoryRepository.findOne(example);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public List<Category> findAllById(Iterable<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    public Optional<Category> findByCategoryName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public Page<Category> findByCategoryNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByCategoryNameContaining(name, pageable);
    }

    @Override
    public List<Category> findByCategoryNameContaining(String name) {
        return categoryRepository.findByCategoryNameContaining(name);
    }
}
