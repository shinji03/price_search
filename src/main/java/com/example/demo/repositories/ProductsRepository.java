package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {

}
