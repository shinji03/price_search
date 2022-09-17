package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Users;
import com.example.demo.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public List<Users> searchAll() {
        // ユーザーデータすべてを取得
        return usersRepository.findAll();
    }

}
