package com.example.demo.forms;

import java.io.Serializable;

public class CategoryForm implements Serializable{

    private static final long serialVersionUID = 1L;

    private String categoryid;
    private String categoryname;

    public String getCategoryid() {
        return categoryid;
    }
    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }
    public String getCategoryname() {
        return categoryname;
    }
    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
