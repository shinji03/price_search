package com.example.demo.controllers.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.models.Users;
import com.example.demo.service.UsersService;


/**
 * ユーザー情報 Controller
 */
@Controller
public class UsersController {

    @Autowired
    UsersService usersService;

    /**
     * ユーザー一覧画面を表示
     * @param model Model
     * @return ユーザー一覧画面のHTML
     */
    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Users> UsersList = usersService.searchAll();
        model.addAttribute("usersList", UsersList);

/*
        //フラッシュメッセージの処理
        if (session.getAttribute("flush") != null) {
            model.addAttribute("flush", session.getAttribute("flush"));
            session.removeAttribute("flush");
        }
*/
        return "views/users/list.html";
    }

}
