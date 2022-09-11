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
            productsRepository.save(p);

            //商品一覧へ遷移
            List<Products> Productslist = productsservice.searchAll();
            mv.addObject("productsList", Productslist);
            mv.setViewName("views/products/list.html");

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
    @RequestMapping(path = "/produts/edit", method = RequestMethod.GET)
    public ModelAndView displayshow(@ModelAttribute ProductsForm productsForm, ModelAndView mv) {

        //System.out.println(proName);

        //選択された商品のidをもとにデータを取得
        Optional<Products> Productslist = productsRepository.findById(productsForm.getId());

        ModelMapper modelMapper = new ModelMapper();
        productsForm = modelMapper.map(Productslist.orElse(new Products()),ProductsForm.class);
        productsForm.setToken(session.getId());

        mv.addObject("productsList", productsForm);

        //カテゴリーのリストの作成
        List<Category> Categorylist = categoryService.searchAll();
        mv.addObject("categoryList", Categorylist);

        mv.setViewName("views/products/edit");

        return mv;

    }

    /*

    public ModelAndView reportsEdit(@ModelAttribute ReportForm reportForm, ModelAndView mv) {

        Optional<Report> e = reportRepository.findById(reportForm.getId());
        // ModelMapperでEntity→Formオブジェクトへマッピング
        ModelMapper modelMapper = new ModelMapper();
        reportForm = modelMapper.map(e.orElse(new Report()), ReportForm.class);

        reportForm.setToken(session.getId());

        mv.addObject("report", reportForm);

        session.setAttribute("report_id", reportForm.getId());

        mv.setViewName("views/reports/edit");

        return mv;
    }*/


}
