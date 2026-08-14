package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public Order createOrder(String fullName, String email, String address, String city, String zipCode, String country, String cardName, String cardNumber, Cart cart) {
        Order order = new Order();
        order.setOrderId("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setFullName(fullName);
        order.setEmail(email);
        order.setAddress(address);
        order.setCity(city);
        order.setZipCode(zipCode);
        order.setCountry(country);
        order.setCardName(cardName);
        
        // Mask card number: e.g., **** **** **** 1234
        if (cardNumber != null && cardNumber.length() >= 4) {
            order.setCardNumberHidden("**** **** **** " + cardNumber.substring(cardNumber.length() - 4));
        } else {
            order.setCardNumberHidden("**** **** **** 1111");
        }

        // Copy cart items
        order.setItems(new ArrayList<>(cart.getItems()));
        order.setSubtotal(cart.getSubtotal());
        order.setDiscount(cart.getDiscountAmount());
        order.setShipping(cart.getShippingCost());
        order.setTotal(cart.getTotal());

        orders.add(order);
        return order;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order getOrderById(String orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId().equalsIgnoreCase(orderId))
                .findFirst()
                .orElse(null);
    }
}
