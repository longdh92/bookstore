package com.myweb.bookstore.controller;

import com.myweb.bookstore.entity.Category;
import com.myweb.bookstore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService cateService;

    @GetMapping("/add")
    public String add(ModelMap model) {
        model.addAttribute("category", new Category());
        return "admin/category/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(ModelMap model, Category category) {
        cateService.save(category);
        model.addAttribute("category",category);
        return list(model);
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable(name = "id") Long id) {
        Optional<Category> opt = cateService.findById(id);
        if(opt.isPresent()){
            model.addAttribute("category",opt.get());
        }
        else{
            return list(model);
        }
        return "admin/category/addOrEdit";
    }

    @GetMapping("/delete/{id}")
    public String delete(ModelMap model,@PathVariable(name = "id") Long id) {
        cateService.deleteById(id);
        return list(model);
    }

    @RequestMapping("/list")
    public String list(ModelMap model) {
        List<Category> list = (List<Category>) cateService.findAll();
        model.addAttribute("category", list);
        return "admin/category/list";
    }

    @RequestMapping("/find")
    public String find(ModelMap model, @RequestParam(defaultValue = "") String name) {
        List<Category> list = cateService.findByNameLikeOrderByName(name);
        model.addAttribute("category",list);
        return "admin/category/find";
    }
}
