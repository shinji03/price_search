package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class toppageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String toppage(Model model) {
        model.addAttribute("message", "Hello");
        return "views/toppage/toppage.html";
    }

    //ログイン画面の呼び出し
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "views/login/loginpage.html";
    }

}
