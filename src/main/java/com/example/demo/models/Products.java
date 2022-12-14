package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 商品情報 Entity
 */
@Table(name = "products")
@Entity
public class Products {

    /**
     * ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * カテゴリーID
     */
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * 名前
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 販売日
     */
    @Column(name = "sells_day", nullable = false)
    private String sells_day;

    /**
     * 価格
     */
    @Column(name = "price", nullable = false)
    private int price;

    /**
     * 詳細
     */
    @Lob
    @Column(name = "detail", nullable = false)
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

    public String getSells_day() {
        return sells_day;
    }

    public void setSells_day(String sells_day) {
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
