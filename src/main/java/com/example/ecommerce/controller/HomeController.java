package com.example.ecommerce.controller;

import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/home"})
    public String home(@RequestParam(value = "category", required = false, defaultValue = "All") String category,
                       @RequestParam(value = "q", required = false) String query,
                       Model model) {
        model.addAttribute("products", productService.searchAndFilterProducts(category, query));
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchQuery", query);
        return "home";
    }
}
