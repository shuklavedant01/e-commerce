package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import java.io.Serializable;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Cart cart = new Cart();

    public Cart getCart() {
        return cart;
    }

    public void addToCart(Product product, int quantity) {
        cart.addItem(product, quantity);
    }

    public void updateCartItem(Long productId, int quantity) {
        cart.updateQuantity(productId, quantity);
    }

    public void removeFromCart(Long productId) {
        cart.removeItem(productId);
    }

    public void applyCoupon(String couponCode) {
        cart.setCouponCode(couponCode);
    }

    public void clearCart() {
        cart.clear();
    }
}
