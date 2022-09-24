package com.example.demo.details;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.models.Users;

public class DbUserDetails extends User{

    //ユーザ情報。
    private final Users user;

    public DbUserDetails(Users user,
            Collection<GrantedAuthority> authorities) {

        super(user.getName(),user.getPassword(),true, true, true, true, authorities);

        this.user = user;
    }

    public Users getAccount() {
        return user;
    }

}
