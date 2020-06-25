package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.Admin;
import com.myweb.sportthanhbinh.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping(value = "/admin/management")
public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @GetMapping(value = "/create")
    public String creat(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null || adminRepository.findById(admin.getId()).get().getRole() >0){
            return "redirect:/admin/home";
        }
        model.addAttribute("admin", new Admin());
        return "admin/adminManagement/detail";
    }

    @PostMapping(value = "/create")
    public String create(Model model, Admin admin) {
        try {
            if (!adminRepository.findAdminByEmail(admin.getEmail()).isPresent()) {
                admin.setStatus(true);
                admin.setPassword("123");
                admin.setRole(1);
                adminRepository.save(admin);
                model.addAttribute("alert", "alert alert-success");
                model.addAttribute("message", "create admin successfully");
            } else {
                model.addAttribute("alert", "alert alert-danger");
                model.addAttribute("message", "create admin failed!!");
            }

        } catch (Exception e) {
            return "redirect:/404";
        }
        return "admin/adminManagement/detail";
    }
    @GetMapping(value = "/enable/{id}")
    public String enable(@PathVariable("id")Long id, Model model, RedirectAttributes ra){
        Optional<Admin> optional = adminRepository.findById(id);
        try{
            if(optional.isPresent() ){
                Admin admin = optional.get();
                admin.setStatus(false);
                adminRepository.save(admin);
                ra.addFlashAttribute("message","successfully");
                ra.addFlashAttribute("alert", "alert alert-success");
            }else {
                return "redirect:/404";
            }
        }catch (Exception e){
            ra.addFlashAttribute("message","failed");
            ra.addFlashAttribute("alert", "alert alert-danger");
            return "redirect:/admin/management/list";
        }
        return "redirect:/admin/management/list";
    }
    @GetMapping(value = "/active/{id}")
    public String active(@PathVariable("id")Long id, Model model, RedirectAttributes ra){
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        try{
            if(optionalAdmin.isPresent()){
                Admin admin = optionalAdmin.get();
                admin.setStatus(true);
                adminRepository.save(admin);
                ra.addFlashAttribute("message","successfully");
                ra.addFlashAttribute("alert", "alert alert-success");
            }else {
                return "redirect:/404";
            }
        }catch (Exception e){
            ra.addFlashAttribute("message","failed");
            ra.addFlashAttribute("alert", "alert alert-danger");
            return "redirect:/admin/management/list";
        }
        return "redirect:/admin/management/list";
    }

    @GetMapping("/list")
    public String list(Model model, HttpSession session, @ModelAttribute("alert")String alert, @ModelAttribute("message")String mesage){
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null || adminRepository.findById(admin.getId()).get().getRole() >0){
            return "redirect:/admin/home";
        }
        model.addAttribute("alert", alert);
        model.addAttribute("message", mesage);

        model.addAttribute("admins", adminRepository.findAllByRole(0));
        return "admin/adminManagement/list";
    }
    @GetMapping(value = "changePassword")
    public String changePassword(HttpSession session, @RequestParam("newPassword") String newPassword,
                                 @RequestParam("oldPassword") String oldPassword, RedirectAttributes ra) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/login";
        }
        Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());
        if (optionalAdmin.isPresent() && optionalAdmin.get().getPassword().equals(oldPassword)){
            Admin admin1 =optionalAdmin.get();
            admin1.setPassword(newPassword);
            adminRepository.save(admin1);
            ra.addFlashAttribute("message","change password successfully");
            return "redirect:/admin/home";

        }else {
            ra.addFlashAttribute("message","change password failed!! \n try again");
            return "redirect:/admin/home";
        }
    }


}
