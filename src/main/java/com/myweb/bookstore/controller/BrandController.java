package com.myweb.bookstore.controller;

import com.myweb.bookstore.entity.Brand;
import com.myweb.bookstore.entity.Product;
import com.myweb.bookstore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/admin/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/add")
    public String add(ModelMap model) {
        model.addAttribute("brand", new Brand());
        return "admin/brand/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(ModelMap model, Brand brand) {
        brandService.save(brand);
        model.addAttribute("brand",brand);
        return list(model);
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable(name = "id") Long id) {
        Optional<Brand> opt = brandService.findById(id);
        if(opt.isPresent()){
            model.addAttribute("brand",opt.get());
        }
        else{
            return list(model);
        }
        return "admin/brand/addOrEdit";
    }

    @GetMapping("/delete/{id}")
    public String delete(ModelMap model, @PathVariable(name = "id") Long id, RedirectAttributes ra) {
        try {
            Optional<Brand> opt = brandService.findById(id);
            if(opt.isPresent()){
                opt.get().setStatus("Out Stock");
                brandService.save(opt.get());
                model.addAttribute("brand",opt.get());
            }
        }catch (Exception e){
            ra.addFlashAttribute("message","Don't delete!");
            e.printStackTrace();
        }
//        brandService.deleteById(id);
        return list(model);
    }

    @RequestMapping("/list")
    public String list(ModelMap model) {
        List<Brand> list = (List<Brand>) brandService.findAll();
        model.addAttribute("brand", list);
        return "admin/brand/list";
    }
}
