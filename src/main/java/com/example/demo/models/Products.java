package com.example.demo.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Table(name = "products")
@Entity
public class Products {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @Column(name = "category_id", nullable = false)
    private Category category;;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sells_day", nullable = false)
    private Date sells_day;

    @Column(name = "price", nullable = false)
    private int price;

    @Lob
    @Column(name = "detali", nullable = false)
    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSells_day() {
        return sells_day;
    }

    public void setSells_day(Date sells_day) {
        this.sells_day = sells_day;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
