package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {

    List<Products> findByName(String name);
}
