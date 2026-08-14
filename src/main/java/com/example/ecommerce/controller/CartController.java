package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                            RedirectAttributes redirectAttributes) {
        Optional<Product> productOpt = productService.getProductById(productId);
        if (productOpt.isPresent()) {
            cartService.addToCart(productOpt.get(), quantity);
            redirectAttributes.addFlashAttribute("successMessage", "Added \"" + productOpt.get().getName() + "\" to cart!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found.");
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("productId") Long productId,
                             @RequestParam("quantity") int quantity,
                             RedirectAttributes redirectAttributes) {
        cartService.updateCartItem(productId, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Updated quantity in cart.");
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId,
                                 RedirectAttributes redirectAttributes) {
        cartService.removeFromCart(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Removed item from cart.");
        return "redirect:/cart";
    }

    @PostMapping("/cart/coupon")
    public String applyCoupon(@RequestParam("couponCode") String couponCode,
                              RedirectAttributes redirectAttributes) {
        cartService.applyCoupon(couponCode);
        if ("WELCOME10".equalsIgnoreCase(couponCode)) {
            redirectAttributes.addFlashAttribute("successMessage", "Coupon 'WELCOME10' applied! 10% discount.");
        } else if ("FREESHIP".equalsIgnoreCase(couponCode)) {
            redirectAttributes.addFlashAttribute("successMessage", "Coupon 'FREESHIP' applied! Free shipping.");
        } else if (couponCode == null || couponCode.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("infoMessage", "Coupon removed.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid coupon code.");
        }
        return "redirect:/cart";
    }
}
