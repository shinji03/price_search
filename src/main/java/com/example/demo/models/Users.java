package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ユーザー情報 Entity
 */

@Entity
@Table(name = "users")
public class Users {

    /**
     * ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 名前
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * パスワード
     */
    @Column(name = "password",length = 64,  nullable = false)
    private String password;

    /**
     * 管理者権限(制作者=1, 利用者=0)
     */
    @Column(name = "admin_flag", nullable = false)
    private Integer adminFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(Integer adminFlag) {
        this.adminFlag = adminFlag;
    }

}
