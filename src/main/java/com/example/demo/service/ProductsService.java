package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Products;
import com.example.demo.repositories.ProductsRepository;

@Service
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

    public List<Products> searchProductName(String proName) {
        // 入力内容に応じて、商品の詳細を検索
        return productsRepository.findByName(proName);
    }

    public String[] removeElement(String[] pronames, String item) {
        return Arrays.stream(pronames)
                .filter(proname -> !proname.equals(item))
                .toArray(String[]::new);
    }

}
