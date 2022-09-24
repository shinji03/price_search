package com.example.demo.controllers.users;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.forms.UsersForm;
import com.example.demo.models.Users;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.service.UsersService;
import com.example.demo.validators.UsersValidator;


/**
 * ユーザー情報 Controller
 */
@Controller
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    HttpSession session; // セッション

    @Autowired
    UsersValidator validator; // バリデーター

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


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

    /**
     * ユーザー新規登録画面を表示
     * @param model Model
     * @return ユーザー一覧画面のHTML
     */
    @RequestMapping(value = "/users/new", method = RequestMethod.GET)
    public ModelAndView displayNew(@ModelAttribute UsersForm usersForm, ModelAndView mv) {

        usersForm.setToken(session.getId());

        mv.addObject("user", usersForm);
        mv.setViewName("views/users/new.html");

        return mv;
    }

    /**
     * ユーザー登録する
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    @Transactional // メソッド開始時にトランザクションを開始、終了時にコミットする
    public ModelAndView displayCreate(@ModelAttribute UsersForm usersForm, ModelAndView mv) {

        if (usersForm.getToken() != null && usersForm.getToken().equals(session.getId())) {
            Users u = new Users();
            Integer admin_frag = 0;//一般ユーザーの設定

            //ユーザーの追加
            u.setName(usersForm.getName());
            u.setAdminFlag(admin_frag);
            u.setPassword(passwordEncoder.encode(usersForm.getPassword()));

            List<String> errors = validator.validate(u,true, true);

            if (errors.size() > 0) {
                usersForm.setToken(session.getId());
                mv.addObject("user",usersForm);
                mv.addObject("errors", errors);
                mv.setViewName("views/users/new");
            } else {
                usersRepository.save(u);
                session.setAttribute("flush", "登録が完了しました");

                mv = new ModelAndView("redirect:/");
            }
        } else {
            //トークンIDが異なる場合
            mv.setViewName("views/toppage/toppage.html");
        }
        return mv;

    }


}
