package com.myweb.bookstore.controller;

import com.myweb.bookstore.entity.Cart;
import com.myweb.bookstore.entity.CartDetail;
import com.myweb.bookstore.entity.Customer;
import com.myweb.bookstore.entity.Product;
import com.myweb.bookstore.repository.CartDetailReponsitory;
import com.myweb.bookstore.repository.CartReponsitory;
import com.myweb.bookstore.repository.ProductReponsitory;
import com.myweb.bookstore.service.CartDetailService;
import com.myweb.bookstore.service.CartService;
import com.myweb.bookstore.service.CustomerService;
import com.myweb.bookstore.service.ProductService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/trang-chu")
public class CartController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartReponsitory cartReponsitory;

    @Autowired
    private CartDetailReponsitory cartDetailReponsitory;

    @GetMapping("/addtocart/{id}")
    public String addToCart(@PathVariable(name = "id") Long id, ModelMap model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Optional<Product> opt = productService.findById(id);
        if (opt.isPresent()) {
            Cart carts = cartReponsitory.findByCustomer(customer.getId());
            if (carts == null) {
                Cart entity = new Cart();
                CartDetail cartDetail = new CartDetail();
                entity.setCustomer(customer);
                cartService.save(entity);
                cartDetail.setCart(entity);
                cartDetail.setProduct(opt.get());
                cartDetail.setQuantity(1);
                cartDetailService.save(cartDetail);
                return "redirect:/trang-chu";
            } else {
                for (CartDetail c : carts.getCartDetailList()) {
                    if (c.getProduct().getId().equals(opt.get().getId())) {
                        c.setQuantity(c.getQuantity() + 1);
                        cartDetailService.save(c);
                        return "redirect:/trang-chu";
                    }
                }
                CartDetail cartDetail1 = new CartDetail();
                cartDetail1.setCart(carts);
                cartDetail1.setProduct(opt.get());
                cartDetail1.setQuantity(1);
                cartDetailService.save(cartDetail1);
                return "redirect:/trang-chu";
            }
        }
        return "redirect:/trang-chu";
    }

    @GetMapping("/viewcart")
    public String viewCart(ModelMap model,HttpSession session){
        if(session.getAttribute("customer")!=null) {
            Customer customer = (Customer) session.getAttribute("customer");
            model.addAttribute("customername", customer.getName());
            List<CartDetail> list = cartDetailReponsitory.findByCustomer(customer.getId());
            model.addAttribute("cartdetail", list);
            Double total = cartReponsitory.total(customer.getId());
            model.addAttribute("total", total);
        }
        return "web/cart/viewCart";
    }

    @PostMapping("/updateCart")
    public String updateCart( HttpServletRequest request, HttpSession session, RedirectAttributes ra){
        String[] quantity = request.getParameterValues("quantity");
        Customer customer = (Customer) session.getAttribute("customer");
        List<CartDetail> list = cartDetailReponsitory.findByCustomer(customer.getId());
        for (int i=0;i<list.size();i++){
                if(Integer.parseInt(quantity[i])<list.get(i).getProduct().getQuantity()){
                    list.get(i).setQuantity(Integer.parseInt(quantity[i]));
                    cartDetailService.save(list.get(i));
                    ra.addFlashAttribute("message","Update successfull!");
                }
                else {
                    ra.addFlashAttribute("message","Excess quantity of stock");
                }
            }
        return "redirect:/trang-chu/viewcart";
    }

    @GetMapping("/deleteCart/{id}")
    public String deleteCart(HttpSession session,@PathVariable("id")Long id,RedirectAttributes ra){
        Customer customer = (Customer) session.getAttribute("customer");
        Optional<CartDetail> cartDetail = cartDetailService.findById(id);
        if(cartDetail.isPresent()){
            cartDetailService.deleteById(cartDetail.get().getId());
            ra.addFlashAttribute("message","Delete cart success!");
        }
        return "redirect:/trang-chu/viewcart";
    }


}
