package com.claz.services;

import java.util.List;

import com.claz.models.Cart;

public interface CartService {

	List<Cart> getAllItemInCart(String username);

	Cart addItemToCart(Cart cart);

	void deleteItemInCart(int itemID);

	Cart findItemById(int id);

	Cart reduceQuantityItemInCart(int itemID);

	void deleteAllItemInCart();
}