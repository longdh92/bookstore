package com.myweb.bookstore.controller;

import com.myweb.bookstore.entity.*;
import com.myweb.bookstore.repository.CartDetailReponsitory;
import com.myweb.bookstore.service.CategoryService;
import com.myweb.bookstore.service.CustomerService;
import com.myweb.bookstore.service.ProductService;
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

    @GetMapping(value = "/admin/home")
    public String home(HttpSession session) {
        if(session.getAttribute("customer")!=null){
            Customer customer = (Customer)session.getAttribute("customer");
            for (Role r :customer.getRoleList()){
                if(r.getId().equals(Long.parseLong(String.valueOf(1)))){
                    return "admin/home";
                }
            }
        }
        return "redirect:/trang-chu";
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
//            if(BCrypt.checkpw(password,customer.getPassword())){
//                return "/trang-chu";
//            }
            model.addAttribute("message", "Account does not exist");
            return  "login";
        }
        else{
            if (customer.getPassword().equals(password)){
                session.setAttribute("customer",customer);
                for (Role c: customer.getRoleList()){
                    if(c.getId().equals(Long.parseLong(String.valueOf(1)))){
                        return  "redirect:/admin/home";
                    }
                    else{
                        return "redirect:/trang-chu";
                    }
                }
            }
            else {
                model.addAttribute("message", "Your password is incorrect");
                return  "login";
            }
        }
        return  "redirect:/admin/home";
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
//        List<Category> listcate = (List<Category>) categoryService.findAll();
//        model.addAttribute("category",listcate);
//        List<Product> list = (List<Product>) productService.findAll();
//        model.addAttribute("product", list);
//        return "web/index";
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
