package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.*;
import com.myweb.sportthanhbinh.repository.BrandReponsitory;
import com.myweb.sportthanhbinh.repository.CartDetailReponsitory;
import com.myweb.sportthanhbinh.service.CategoryService;
import com.myweb.sportthanhbinh.service.CustomerService;
import com.myweb.sportthanhbinh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartDetailReponsitory cartDetailReponsitory;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BrandReponsitory brandReponsitory;

    @GetMapping(value = "/admin/home")
    public String home(HttpSession session) {
            Admin admin = (Admin) session.getAttribute("admin");
            if(admin!=null){
                    return "/admin/home";
            }
            else {
                return "redirect:/admin/login";
            }
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        return "redirect:/trang-chu";
    }

    @PostMapping("/login")
    public String login(HttpSession session, ModelMap model, @RequestParam("username")String email, @RequestParam("password")String password){
        Customer customer = customerService.findByEmail(email);
        System.out.println(customer);
        if(customer==null) {
            model.addAttribute("message", "Account does not exist");
            return  "login";
        }
        else{
            if (customer.getPassword().equals(password)){
                session.setAttribute("customer",customer);
                for (Role c: customer.getRoleList()){
                    if(c.getId().equals(Long.parseLong(String.valueOf(1)))){
                        return "redirect:/trang-chu";
                    }
                }
            }
            else {
                model.addAttribute("message", "Your password is incorrect");
                return  "login";
            }
        }
        return "redirect:/trang-chu";
    }

    @GetMapping(value = "/trang-chu")
    public String index(ModelMap model,HttpSession session) {
        if(session.getAttribute("customer")!=null){
            Customer customer = (Customer) session.getAttribute("customer");
            model.addAttribute("customername",customer.getName());
            List<CartDetail> list = cartDetailReponsitory.findByCustomer(customer.getId());
            model.addAttribute("cartdetail",list );

        }
        return findPaginated(1, model);
    }

    @GetMapping("/trang-chu/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, ModelMap model) {
        int pageSize = 6;
        Page<Product> page = productService.findPaginated(pageNo, pageSize);
        List<Product> productList = page.getContent();
        model.addAttribute("currentPage1", pageNo);
        model.addAttribute("totalPages1", page.getTotalPages());
        model.addAttribute("totalItems1", page.getTotalElements());
        model.addAttribute("product", productList);
        List<Category> listcate = (List<Category>) categoryService.findAll();
        model.addAttribute("category", listcate);
        List<Brand> listBrand = (List<Brand>) brandReponsitory.findAll();
        model.addAttribute("brand",listBrand);
        return "web/index";
    }

    @GetMapping("/trang-chu/product/{id}")
    public String productDetail(ModelMap model, @PathVariable(name = "id") Long id) {
        Optional<Product> opt = productService.findById(id);

        if (opt.isPresent()) {
            model.addAttribute("product", opt.get());
        }
        List<Category> listcategory = (List<Category>) categoryService.findAll();
        model.addAttribute("category", listcategory);
        return "web/product/productDetail";
    }

    @GetMapping("/trang-chu/category/{id}")
    public String productByCategory(HttpSession session,ModelMap model, @PathVariable(name = "id") Long id) {
        Optional<Category> opt = categoryService.findById(id);
        model.addAttribute("customer",session.getAttribute("customer"));
        if (opt.isPresent()) {
            model.addAttribute("product", opt.get().getProductList());
        }
        List<Category> listcategory = (List<Category>) categoryService.findAll();
        model.addAttribute("category", listcategory);
        return "web/category/productBycategory";
    }


}
