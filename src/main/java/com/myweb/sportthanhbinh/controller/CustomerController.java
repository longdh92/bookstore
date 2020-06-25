package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.Customer;
import com.myweb.sportthanhbinh.entity.Role;
import com.myweb.sportthanhbinh.repository.RoleReponsitory;
import com.myweb.sportthanhbinh.service.CustomerService;
import com.myweb.sportthanhbinh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleReponsitory roleReponsitory;

    private  MainController mainController;
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model) {
        model.addAttribute("customer", new Customer());

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(Customer entity, @RequestParam("gender")String gender) {
        entity.setGender(gender);
        entity.setRegisterdate(Calendar.getInstance().getTime());
//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = roleService.findById(Long.parseLong(String.valueOf(1)));
        if (role.isPresent()){
            roles.add(role.get());
            entity.setRoleList(roles);
        }
        customerService.save(entity);
        return "redirect:login";
    }




}
