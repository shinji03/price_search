package com.example.demo.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.models.Category;
import com.example.demo.models.Products;

@Component
public class ProductsValidator {

    public List<String>validate(Products p){
        List<String> errors = new ArrayList<String>();


        String category_error = _validateCategory(p.getCategory());
        if (!category_error.equals("")) {
            errors.add(category_error);
        }

        String name_error = _validateName(p.getName());
        if (!name_error.equals("")) {
            errors.add(name_error);
        }
        return errors;

    }

    private static String _validateCategory(Category category) {
        if (category == null || category.equals("")) {
            return "カテゴリーが選択されていない";
        }

        return "";
    }


    private static String _validateName(String name) {
        if (name == null || name.equals("")) {
            return "登録する商品の名前が入力されていない";
        }

        return "";
    }

}
