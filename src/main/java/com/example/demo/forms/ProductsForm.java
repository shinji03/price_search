package com.example.demo.forms;

import com.example.demo.models.Products;

public class ProductsForm extends Products {

    private String proName;

    private String token;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
