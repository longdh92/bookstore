package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.*;

import com.myweb.sportthanhbinh.repository.BillDetailReponsitory;
import com.myweb.sportthanhbinh.repository.BillReponsitory;
import com.myweb.sportthanhbinh.repository.CartDetailReponsitory;
import com.myweb.sportthanhbinh.repository.CartReponsitory;
import com.myweb.sportthanhbinh.service.CartDetailService;
import com.myweb.sportthanhbinh.service.CartService;
import com.myweb.sportthanhbinh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
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

    @Autowired
    private BillReponsitory billReponsitory;

    @Autowired
    private BillDetailReponsitory billDetailReponsitory;

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

    @GetMapping("/checkOut")
    public String checkOutView(HttpSession session,ModelMap model,@ModelAttribute ("message")String message){
        if(session.getAttribute("customer")!=null){
            Customer customer = (Customer)session.getAttribute("customer");
            model.addAttribute("customername",customer.getName());
            model.addAttribute("customer",customer);
            List<CartDetail> list = cartDetailReponsitory.findByCustomer(customer.getId());
            model.addAttribute("cartdetail", list);
            Double total = cartReponsitory.total(customer.getId());
            model.addAttribute("total", total);
            model.addAttribute("message",message);
            System.out.println(message);
            return "web/cart/checkOut";
        }
        return "redirect:/login";
    }


    @PostMapping("checkOut")
    public String checkOutSubmit(HttpSession session,@RequestParam("address")String address,RedirectAttributes ra){
        Customer customer = (Customer)session.getAttribute("customer");
        List<CartDetail> list = cartDetailReponsitory.findByCustomer(customer.getId());
        if(list.isEmpty()){
            ra.addFlashAttribute("message","Cart is empty!");
            return "redirect:/trang-chu/checkOut";
        }
        for (CartDetail c:list){
            if(c.getQuantity()>c.getProduct().getQuantity()){
                ra.addFlashAttribute("message","Quantity product out of stock!");
                return "redirect:/checkOut";
            }
        }
        Double total = cartReponsitory.total(customer.getId());
        Bill bill = new Bill();
        bill.setCustomer(customer);
        bill.setOrderdate(Calendar.getInstance().getTime());
        bill.setTotal(total);
        bill.setAddress(address);
        billReponsitory.save(bill);
        for (CartDetail c:list){
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setQuantity(c.getQuantity());
            billDetail.setProduct(c.getProduct());
            billDetailReponsitory.save(billDetail);
            cartDetailService.deleteById(c.getId());
        }
        ra.addFlashAttribute("message","Check Out Success!");
        return "redirect:/trang-chu/checkOut";
    }
}
