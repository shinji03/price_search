package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.details.DbUserDetails;
import com.example.demo.models.Users;
import com.example.demo.repositories.UsersRepository;

@Service
public class UsersService implements UserDetailsService{

    @Autowired
    UsersRepository usersRepository;

    public List<Users> searchAll() {
        // ユーザーデータすべてを取得
        return usersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        //DBからユーザ情報を取得。
        Users user = Optional.ofNullable(usersRepository.findByName(name))
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new DbUserDetails(user, getAuthorities(user));
    }

    /**
     * 認証が通った時にこのユーザに与える権限の範囲を設定する。
     * @param account DBから取得したユーザ情報。
     * @return 権限の範囲のリスト。
     */
    private Collection<GrantedAuthority> getAuthorities(Users user) {
        //認証が通った時にユーザに与える権限の範囲を設定する。
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

}
