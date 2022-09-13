package com.example.demo.controllers.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.forms.ProductsForm;
import com.example.demo.models.Category;
import com.example.demo.models.Products;
import com.example.demo.repositories.ProductsRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductsService;
import com.example.demo.validators.ProductsValidator;

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

    @Autowired
    HttpSession session; // セッション

    @Autowired
    ProductsValidator validator; // バリデーター


    /**
     * 商品情報一覧画面を表示
     * @param model Model
     * @return 商品情報一覧画面のHTML
     */
    @RequestMapping(value = "/products/list", method = RequestMethod.GET)
    public String displayList(Model model) {
        List<Products> Productslist = productsservice.searchAll();
        model.addAttribute("productsList", Productslist);


        //フラッシュメッセージの処理
        if (session.getAttribute("flush") != null) {
            model.addAttribute("flush", session.getAttribute("flush"));
            session.removeAttribute("flush");
        }

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

        //何も入力されていない状態を排除する（何も入力されていない場合は00が入力される）
        pronames = productsservice.removeElement(pronames, "00");

        //for文で使用する新たなリストの作成
        List<Products> Productslist = new ArrayList<Products>();
        //合計金額の初期値を0円に設定
        Integer Price = 0;

        for (String proname : pronames) {
            Productslist.addAll(productsservice.searchProductName(proname));
            Price = Price + productsservice.searchProductName(proname).get(0).getPrice();
        }

        model.addAttribute("productsList", Productslist);
        model.addAttribute("totalPrice", String.format("%,d", Price));
        return "views/products/result.html";
    }

    /**
     * 商品の登録ページに遷移
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */

    @RequestMapping(value = "/products/new", method = RequestMethod.GET)
    public ModelAndView displayNew(@ModelAttribute ProductsForm productsForm, ModelAndView mv) {

        productsForm.setToken(session.getId());

        //カテゴリーのリストの作成
        List<Category> Categorylist = categoryService.searchAll();
        mv.addObject("categoryList", Categorylist);

        mv.addObject("product", productsForm);
        mv.setViewName("views/products/new.html");

        return mv;
    }

    /**
     * 商品を登録する
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */

    @RequestMapping(value = "/products/create", method = RequestMethod.POST)
    @Transactional // メソッド開始時にトランザクションを開始、終了時にコミットする
    public ModelAndView displayCreate(@ModelAttribute ProductsForm productsForm, ModelAndView mv) {

        if (productsForm.getToken() != null && productsForm.getToken().equals(session.getId())) {
            Products p = new Products();

            //商品の追加
            p.setCategory((Category) productsForm.getCategory());
            p.setName(productsForm.getName());
            p.setSells_day(productsForm.getSells_day());
            p.setPrice(productsForm.getPrice());
            p.setDetail(productsForm.getDetail());

            List<String> errors = validator.validate(p);

            if (errors.size() > 0) {
                productsForm.setToken(session.getId());
                mv.addObject("product", productsForm);
                mv.addObject("errors", errors);
                mv.setViewName("views/products/new");
            } else {
                productsRepository.save(p);
                session.setAttribute("flush", "商品の追加が完了");

                mv = new ModelAndView("redirect:/products/list");
            }
        } else {
            //トークンIDが異なる場合
            mv.setViewName("views/toppage/toppage.html");
        }
        return mv;

    }

    /**
     * 編集画面に遷移する
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */
    @RequestMapping(path = "/products/edit", method = RequestMethod.GET)
    public ModelAndView displayshow(@ModelAttribute ProductsForm productsForm, ModelAndView mv) {

        productsForm.setToken(session.getId());

        //選択された商品のidをもとにデータを取得
        Optional<Products> Productslist = productsRepository.findById(productsForm.getId());

        ModelMapper modelMapper = new ModelMapper();
        productsForm = modelMapper.map(Productslist.orElse(new Products()), ProductsForm.class);
        productsForm.setToken(session.getId());

        mv.addObject("productsList", productsForm);
        session.setAttribute("product_id", productsForm.getId());

        //カテゴリーのリストの作成
        List<Category> Categorylist = categoryService.searchAll();
        mv.addObject("categoryList", Categorylist);

        mv.setViewName("views/products/edit");

        return mv;

    }

    /**
     * 商品の編集を確定する
     * @param model Model
     * @return 検索結果一覧画面のHTML
     */
    @RequestMapping(path = "/products/update", method = RequestMethod.POST)
    @Transactional // メソッド開始時にトランザクションを開始、終了時にコミットする
    public ModelAndView displayUpdate(@ModelAttribute ProductsForm productsForm, ModelAndView mv) {

        if (productsForm.getToken() != null && productsForm.getToken().equals(session.getId())) {

            Optional<Products> opp = productsRepository.findById((Integer) session.getAttribute("product_id"));
            Products p = opp.orElse(null);
            p.setCategory(productsForm.getCategory());
            p.setName((productsForm.getName()));
            p.setSells_day(productsForm.getSells_day());
            p.setPrice(productsForm.getPrice());
            p.setDetail(productsForm.getDetail());
            //編集の確定
            productsRepository.save(p);
            session.removeAttribute("product_id");

            //フラッシュメッセージの追加
            session.setAttribute("flush", "商品の追加が完了");

            // リストにリダイレクト
            mv = new ModelAndView("redirect:/products/list");

        } else {
            //トークンIDが異なる場合
            mv.setViewName("views/toppage/toppage.html");
        }
        return mv;
    }

}
