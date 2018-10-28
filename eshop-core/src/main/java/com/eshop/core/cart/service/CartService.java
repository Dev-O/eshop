package com.eshop.core.cart.service;

import java.util.Set;

import com.eshop.core.cart.model.CartItem;
import com.eshop.core.order.model.Order;

public interface CartService {
    public String createNewCart();
    public void addProduct(String cartId, CartItem cartItem);
    public void removeProduct(String cartId, String productId);

    public void setProductQuantity(String cartId, String productId, int quantity);
    public Set<CartItem> getItems(String cartId);
    public Order createOrder(String cartId, Order order);
}