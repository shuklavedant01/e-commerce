package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String viewCheckout(Model model) {
        Cart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam("fullName") String fullName,
                                  @RequestParam("email") String email,
                                  @RequestParam("address") String address,
                                  @RequestParam("city") String city,
                                  @RequestParam("zipCode") String zipCode,
                                  @RequestParam("country") String country,
                                  @RequestParam("cardName") String cardName,
                                  @RequestParam("cardNumber") String cardNumber,
                                  Model model) {
        Cart cart = cartService.getCart();
        if (cart.getItems().isEmpty()) {
            return "redirect:/cart";
        }

        // Create the order
        Order order = orderService.createOrder(fullName, email, address, city, zipCode, country, cardName, cardNumber, cart);
        
        // Clear the user's cart
        cartService.clearCart();

        return "redirect:/checkout/success/" + order.getOrderId();
    }

    @GetMapping("/checkout/success/{orderId}")
    public String orderSuccess(@PathVariable("orderId") String orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return "redirect:/";
        }
        model.addAttribute("order", order);
        return "order-success";
    }
}
