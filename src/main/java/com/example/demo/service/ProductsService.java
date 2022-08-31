package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.Products;
import com.example.demo.repositories.ProductsRepository;

public class ProductsService {

    /**
     * 商品情報 Repository
     */
    @Autowired
    ProductsRepository productsRepository;

    public List<Products> searchAll() {
        // 商品TBLの内容を全検索
        return productsRepository.findAll();
    }

}
