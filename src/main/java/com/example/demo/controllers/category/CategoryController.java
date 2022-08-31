package com.example.demo.controllers.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.models.Category;
import com.example.demo.service.CategoryService;

/**
 * カテゴリー情報 Controller
 */
@Controller
public class CategoryController {

    /**
     * カテゴリー情報 Service
     */
    @Autowired
    CategoryService categoryService;

    /**
     * カテゴリー情報一覧画面を表示
     * @param model Model
     * @return カテゴリー情報一覧画面のHTML
     */
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public String displayList(Model model) {
      List<Category> Categorylist = categoryService.searchAll();
      model.addAttribute("categorylist", Categorylist);
      return "views/category/list.html";
    }

}
