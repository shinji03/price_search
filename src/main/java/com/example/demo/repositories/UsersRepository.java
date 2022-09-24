package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Users;

/**
 * ユーザー情報 Repository
 */
@Repository
public interface UsersRepository extends JpaRepository <Users, Integer> {

    public Long countByNameEquals(String name);

    //List<Users> findByName(String name);
    //public long countByName (String name);

    public Users findByName(String name);

}
