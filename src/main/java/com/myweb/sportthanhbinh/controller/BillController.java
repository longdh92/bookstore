package com.myweb.sportthanhbinh.controller;

import com.myweb.sportthanhbinh.entity.Bill;
import com.myweb.sportthanhbinh.entity.BillDetail;
import com.myweb.sportthanhbinh.entity.Product;
import com.myweb.sportthanhbinh.repository.BillReponsitory;
import com.myweb.sportthanhbinh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/bill")
public class BillController {
    @Autowired
    private BillReponsitory billReponsitory;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public String viewBill(ModelMap model){
        List<Bill> list = billReponsitory.findAll();
        model.addAttribute("bill",list);
        return "admin/bill/list";
    }

    @GetMapping("/update/{id}")
    public String updateBillActive(@PathVariable(name = "id")Long id){
        Optional<Bill> bill = billReponsitory.findById(id);
        if(bill.isPresent()){
            bill.get().setStatus(2);
            for (BillDetail billDetail:bill.get().getBillDetailList()){
                Optional<Product> optionalProduct = productService.findById(billDetail.getProduct().getId());
                if(optionalProduct.isPresent()){
                    optionalProduct.get().setQuantity(optionalProduct.get().getQuantity()-billDetail.getQuantity());
                    productService.save(optionalProduct.get());
                }
            }
            billReponsitory.save(bill.get());
        }
        return "redirect:/admin/bill/list";
    }

}
