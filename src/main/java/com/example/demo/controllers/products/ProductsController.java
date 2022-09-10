package com.example.demo.controllers.products;

import java.util.ArrayList;
import java.util.List;

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
            //System.out.println(proname);
            //System.out.println(Price);
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

    @RequestMapping(value = "/products/create", method = RequestMethod.GET)
    public String displayCreate() {

        return "views/products/list.html";
    }

    /*

// 作成画面に遷移する内容の書き方
    @RequestMapping(path = "/reports/new", method = RequestMethod.GET)
    public ModelAndView reportsNew(@ModelAttribute ReportForm reportForm, ModelAndView mv) {

        reportForm.setToken(session.getId());
        reportForm.setReportDate(new Date(System.currentTimeMillis()));

        mv.addObject("report", reportForm);

        mv.setViewName("views/reports/new");

        return mv;
    }
    //作成のやり方
    @RequestMapping(path = "/reports/create", method = RequestMethod.POST)
    @Transactional // メソッド開始時にトランザクションを開始、終了時にコミットする
    public ModelAndView reportsCreate(@ModelAttribute ReportForm reportForm, ModelAndView mv) {

        if (reportForm.getToken() != null && reportForm.getToken().equals(session.getId())) {

            Report r = new Report();
            r.setEmployee((Employee)session.getAttribute("login_employee"));

            Date report_date = new Date(System.currentTimeMillis());
            if(reportForm.getReportDate() != null) {
                report_date =reportForm.getReportDate();
            }
            r.setReportDate(report_date);

            r.setTitle(reportForm.getTitle());
            r.setContent(reportForm.getContent());

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            r.setCreated_at(currentTime);
            r.setUpdated_at(currentTime);

            List<String> errors = validator.validate(r);

            if (errors.size() > 0) {
                reportForm.setToken(session.getId());
                mv.addObject("report", reportForm);
                mv.addObject("errors", errors);
                mv.setViewName("views/reports/new");
            } else {
                reportRepository.save(r);
                session.setAttribute("flush", "登録が完了しました。");

                mv = new ModelAndView("redirect:/reports/index"); // リダイレクト
            }
        } else {
            mv.setViewName("views/common/error"); // 真っ白な画面は嫌なので、エラー画面を出す
        }

        return mv;
    }
*/

}
