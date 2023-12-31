package com.PCBuilder.ComponentStore.service;

import com.PCBuilder.ComponentStore.exception.CartNotFoundException;
import com.PCBuilder.ComponentStore.model.Cart;
import com.PCBuilder.ComponentStore.model.CartItem;
import com.PCBuilder.ComponentStore.model.Component;
import com.PCBuilder.ComponentStore.repo.CartRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepo cartRepo;

    @Autowired
    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    // Create a new shopping cart
    public Cart createCart() {
        Cart cart = new Cart();
        cart.setCartStatus("OPEN");
        return cartRepo.save(cart);
    }

    // Get a cart by ID
    public Optional<Cart> getCartById(long cartId) {
        return cartRepo.findById(cartId);
    }

    // Update cart status
    public Cart updateCartStatus(long cartId, String newStatus) {
        Optional<Cart> optionalCart = cartRepo.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setCartStatus(newStatus);
            return cartRepo.save(cart);
        } else {
            // Handle cart not found
            return null;
        }
    }

    public void deleteCart(long cartId) {
        Optional<Cart> optionalCart = cartRepo.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cartRepo.delete(cart);
        } else {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found");
        }
    }
}
