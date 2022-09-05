package com.example.demo.controllers.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.Category;
import com.example.demo.models.Products;
import com.example.demo.repositories.ProductsRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductsService;

/**
 * カテゴリー情報 Controller
 */
@Controller
public class ProductsController {

    /**
     * カテゴリー情報 Service
     */
    @Autowired
    CategoryService categoryService;

    /**
     * 商品情報 Service
     */
    @Autowired
    ProductsService productsservice;

    @Autowired
    ProductsRepository productsRepository;

    /**
     * 商品情報一覧画面を表示
     * @param model Model
     * @return 商品情報一覧画面のHTML
     */
    @RequestMapping(value = "/products/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Products> Productslist = productsservice.searchAll();
        model.addAttribute("productsList", Productslist);
        return "views/products/list.html";
    }

    /**
     * 検索画面を表示
     * @param model Model
     * @return 検索画面のHTML
     */
    @RequestMapping(value = "/products/index", method = RequestMethod.GET)
    public String displayIndex(Model model) {

        //カテゴリーのリストの作成
        List<Category> Categorylist = categoryService.searchAll();
        model.addAttribute("categoryList", Categorylist);

        //商品リストの作成
        List<Products> Productslist = productsservice.searchAll();
        model.addAttribute("productsList", Productslist);
        return "views/products/index.html";
    }

    /**
     * 検索結果を表示
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */
    @RequestMapping(value = "/products/result", method = RequestMethod.GET)
    public String displayResult(@RequestParam(defaultValue = "NOT PARAM") String proName, Model model) {

        //送られてきた値をsplitを使って分ける
        String[] pronames = proName.split(",");
        List<Products> Productslist = new ArrayList<Products>();
        //合計金額の初期値を0円に設定
        Integer Price = 0;

        for (String proname : pronames) {
            Productslist.addAll(productsservice.searchProductName(proname));
            Price = Price + productsservice.searchProductName(proname).get(0).getPrice();
            //System.out.println(proname);
            //System.out.println(Price);
        }

        model.addAttribute("productsList", Productslist);
        model.addAttribute("totalPrice", String.format("%,d", Price));
        return "views/products/result.html";
    }

}
