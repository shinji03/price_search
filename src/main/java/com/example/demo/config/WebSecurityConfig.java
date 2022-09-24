package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                //検索機能の使用は可能
                .mvcMatchers("/products/index", "/products/result", "/products/getProductsData", "/users/**", "/login")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //ログインページ
                .loginPage("/login")
                //ログイン処理を実行するパス
                .loginProcessingUrl("/authenticate")
                //ログイン時のキー：名前
                .usernameParameter("name")
                //ログイン時のパスワード
                .passwordParameter("password")
                //ログインエラー時の遷移先 ※パラメーターに「error」を付与
                .failureUrl("/login?error")
                //ログイン成功時の遷移先
                .defaultSuccessUrl("/", true);
        return http.build();

    }

    //パスワードをハッシュ化する部分
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
