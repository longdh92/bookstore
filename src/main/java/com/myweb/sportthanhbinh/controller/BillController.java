package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.Bill;
import com.myweb.sportthanhbinh.repository.BillReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/bill")
public class BillController {
    @Autowired
    private BillReponsitory billReponsitory;

    @GetMapping("/list")
    public String viewBill(ModelMap model){
        List<Bill> list = billReponsitory.findAll();
        model.addAttribute("bill",list);
        return "admin/bill/list";
    }

}
