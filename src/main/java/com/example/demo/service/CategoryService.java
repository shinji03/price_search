package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;

/**
 * カテゴリー情報 Service
 */
@Service
public class CategoryService {

    /**
     * カテゴリー情報 Repository
     */
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> searchAll() {
        // カテゴリーTBLの内容を全検索
        return categoryRepository.findAll();
    }

}
