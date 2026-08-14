package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String viewAdmin(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("newProduct", new Product());
        return "admin";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("newProduct") Product product,
                             RedirectAttributes redirectAttributes) {
        if (product.getRating() == null) product.setRating(5.0);
        if (product.getStock() == null) product.setStock(10);
        if (product.getImageUrl() == null || product.getImageUrl().trim().isEmpty()) {
            product.setImageUrl("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500");
        }
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("successMessage", "Product \"" + product.getName() + "\" added successfully!");
        return "redirect:/admin";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@RequestParam("id") Long id,
                                RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully.");
        return "redirect:/admin";
    }
}
