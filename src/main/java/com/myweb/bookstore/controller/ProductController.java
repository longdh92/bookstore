package com.myweb.bookstore.controller;

import com.myweb.bookstore.entity.Brand;
import com.myweb.bookstore.entity.Category;
import com.myweb.bookstore.entity.Product;
import com.myweb.bookstore.repository.ProductReponsitory;
import com.myweb.bookstore.service.BrandService;
import com.myweb.bookstore.service.CategoryService;
import com.myweb.bookstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductReponsitory productReponsitory;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/add")
    public String add(ModelMap model) {
        Product product = new Product();
        List<Category> listcate = (List<Category>) categoryService.findAll();
        List<Brand> listbrand = (List<Brand>) brandService.findAll();
        model.addAttribute("brand",listbrand);
        model.addAttribute("product", product);
        model.addAttribute("category", listcate);
        return "admin/product/addOrEdit";
    }

    @PostMapping("/saveOrUpdate")
    public String saveOrUpdate(ModelMap model, Product product, @RequestParam("file") MultipartFile file) throws IOException {
        Path path = Paths.get("D:\\java5\\bookstore\\src\\main\\resources\\static\\bookimage/");
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, path.resolve(Objects.requireNonNull(file.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
            product.setImage(file.getOriginalFilename().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
        productService.save(product);
        model.addAttribute("product", product);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(ModelMap model, @PathVariable(name = "id") Long id) {
        Optional<Product> opt = productReponsitory.findById(id);
        if (opt.isPresent()) {
            model.addAttribute("product", opt.get());
        }
        List<Category> listcate = (List<Category>) categoryService.findAll();
        List<Brand> listbrand = (List<Brand>) brandService.findAll();
        model.addAttribute("category",listcate);
        model.addAttribute("brand",listbrand);
        return "admin/product/addOrEdit";
    }

    @GetMapping("/delete/{id}")
    public String delete(ModelMap model, @PathVariable(name = "id") Long id, RedirectAttributes ra) {
        try {
            Optional<Product> opt = productReponsitory.findById(id);
            if(opt.isPresent()){
                opt.get().setStatus("Out Stock");
                productService.save(opt.get());
                model.addAttribute("product",opt.get());
            }
        }catch (Exception e){
            ra.addFlashAttribute("message","Don't delete!");
            e.printStackTrace();
        }
        return "redirect:/admin/product/list";
    }

    @GetMapping(value = "/list")
    public String viewListpage(ModelMap model,@ModelAttribute("message") String message) {
        model.addAttribute("message",message);
        return findPaginated(1, model);
    }

    @GetMapping("/list/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,ModelMap model) {
        int pageSize = 5;
        Page<Product> page = productService.findPaginated(pageNo, pageSize);
        List<Product> productList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("product", productList);
        return "admin/product/list";
    }

}
