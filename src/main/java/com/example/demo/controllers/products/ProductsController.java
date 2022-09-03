package com.example.demo.controllers.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.models.Products;
import com.example.demo.service.ProductsService;

/**
 * カテゴリー情報 Controller
 */
@Controller
public class ProductsController {

    /**
     * 商品情報 Service
     */
    @Autowired
    ProductsService productsservice;

    /**
     * 商品情報一覧画面を表示
     * @param model Model
     * @return 商品情報一覧画面のHTML
     */
    @RequestMapping(value = "/products/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Products> Productslist = productsservice.searchAll();
        model.addAttribute("productslist", Productslist);
        return "views/products/list.html";
    }

    /**
     * 検索結果画面を表示
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */
    @RequestMapping(value = "/products/index", method = RequestMethod.GET)
    public String displayIndex(Model model) {
        //List<Products> Productslist = productsservice.searchAll();
        //model.addAttribute("productslist", Productslist);
        return "views/products/index.html";
    }

}
