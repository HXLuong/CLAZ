package com.claz.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.claz.models.Cart;
import com.claz.models.Customer;
import com.claz.models.Product;
import com.claz.services.CartService;
import com.claz.services.CustomerService;
import com.claz.services.ProductService;

import java.util.List;
import java.util.Random;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/carts")
public class CartRestController {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	// CRUD item in cart
	@GetMapping("{username}")
	public ResponseEntity<List<Cart>> getAllItem(@PathVariable("username") String username) {
		List<Cart> cartList = cartService.getAllItemInCart(username);
		return ResponseEntity.ok(cartList);
	}

	@DeleteMapping("/delete/{itemID}")
	public ResponseEntity<Cart> deleteItem(@PathVariable("itemID") int itemID) {
		cartService.deleteItemInCart(itemID);
		return ResponseEntity.ok(null);
	}

	@PutMapping("/update/{itemID}")
	public ResponseEntity<Cart> reduceQuantity(@PathVariable("itemID") int itemID) {
		Cart cart = cartService.reduceQuantityItemInCart(itemID);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/add")
	public ResponseEntity<Cart> addItemToCart(@RequestParam("productID") int productID,
			@RequestParam("username") String username) {
		Product product = productService.findById(productID).get();

		Customer customer = customerService.findByUsername(username);

		Random random = new Random();
		int id = 100000 + random.nextInt(900000);

		Cart item = new Cart();
		item.setId(id);
		item.setName(product.getName());
		item.setPrice(product.getPrice());
		item.setImage(product.getImage());
		item.setCustomer(customer);
		item.setProductID(product.getId());
		item.setDiscount(product.getDiscount());
		
		Cart cart = cartService.addItemToCart(item);
		return ResponseEntity.ok(cart);
	}

}
