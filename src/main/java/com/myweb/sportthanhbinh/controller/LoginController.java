package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.Admin;
import com.myweb.sportthanhbinh.repository.AdminReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/")
public class LoginController {
    @Autowired
    private AdminReponsitory adminReponsitory;

    @GetMapping(value = "login")
    public String list(Model model){
        return "admin/login_admin";
    }

    @PostMapping(value = "authentic_login")
    public String login(Model model, @RequestParam("email")String email, @RequestParam("password")String password, HttpSession session){
        Optional<Admin> optionalAdmin = adminReponsitory.findAdminByEmail(email);
        if(optionalAdmin.isPresent() && optionalAdmin.get().getPassword().equals(password) && optionalAdmin.get().isStatus()){
            session.setAttribute("admin",optionalAdmin.get());
            return "redirect:/admin/home";
        }else {
            model.addAttribute("message", "login failed");
            return "admin/login_admin";
        }
    }

    @GetMapping(value = "logout")
    public String logoutOut(Model model,HttpSession session){
        session.invalidate();
        return "redirect:/admin/login";
    }

}
