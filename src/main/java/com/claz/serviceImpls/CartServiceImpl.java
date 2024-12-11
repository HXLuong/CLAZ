package com.claz.serviceImpls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claz.models.Cart;
import com.claz.models.Product;
import com.claz.repositories.CartRepository;
import com.claz.services.CartService;
import com.claz.services.ProductService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductService productService;

	@Override
	public List<Cart> getAllItemInCart(String username) {
		return cartRepository.findAllByCustomerUsername(username);
	}

	@Override
	public Cart addItemToCart(Cart cart) {
		Optional<Cart> existingItemOpt = cartRepository.findByCustomerAndName(cart.getCustomer(), cart.getName());

		if (existingItemOpt.isPresent()) {
			Product product = productService.findById(cart.getProductID()).get();
			int totalQuantityProduct = product.getQuantity();
			Cart existingItem = existingItemOpt.get();
			if (totalQuantityProduct > existingItem.getQuantity()) {
				existingItem.setQuantity(existingItem.getQuantity() + 1);
			} else {
				existingItem.setQuantity(existingItem.getQuantity());
			}
			return cartRepository.save(existingItem);
		} else {
			cart.setQuantity(1);
			return cartRepository.save(cart);
		}
	}

	@Override
	public void deleteItemInCart(int itemID) {
		cartRepository.delete(findItemById(itemID));
	}

	@Override
	public Cart findItemById(int id) {
		return cartRepository.findById(id).get();
	}

	@Override
	public Cart reduceQuantityItemInCart(int itemID) {
		Cart item = findItemById(itemID);
		Optional<Cart> existingItemOpt = cartRepository.findByCustomerAndName(item.getCustomer(), item.getName());

		if (existingItemOpt.isPresent()) {
			Cart existingItem = existingItemOpt.get();
			if (existingItem.getQuantity() == 1) {
				deleteItemInCart(itemID);
				return null;
			}
			existingItem.setQuantity(existingItem.getQuantity() - 1);
			return cartRepository.save(existingItem);
		} else {
			item.setQuantity(1);
			return cartRepository.save(item);
		}
	}
	
	@Override
	public Cart changeQuantityItemInCart(int itemID, int newQuantity) {
	    Cart item = findItemById(itemID);
	    Optional<Cart> existingItemOpt = cartRepository.findByCustomerAndName(item.getCustomer(), item.getName());

	    if (existingItemOpt.isPresent()) {
	        Cart existingItem = existingItemOpt.get();

	        if (newQuantity < 1) {
	            throw new IllegalArgumentException("Số lượng không thể nhỏ hơn 1.");
	        }

	        existingItem.setQuantity(newQuantity);
	        return cartRepository.save(existingItem);
	    } else {
	        throw new IllegalArgumentException("Sản phẩm không tồn tại trong giỏ hàng.");
	    }
	}

	@Override
	public void deleteAllItemInCart() {
		cartRepository.deleteAll();
	}

}
