package com.claz.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.claz.VNPayConfig;
import com.claz.models.Cart;
import com.claz.models.Customer;
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Product;
import com.claz.services.CartService;
import com.claz.services.CustomerService;
import com.claz.services.OrderDetailService;
import com.claz.services.OrderService;
import com.claz.services.ProductService;

import io.jsonwebtoken.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

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

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

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

	// Payment
	@GetMapping("/vnpay-callback")
	public ResponseEntity<Void> paymentCallback(HttpServletRequest request) {
		String orderID = request.getParameter("vnp_TxnRef");
		String amount = request.getParameter("vnp_Amount");
		String status = request.getParameter("vnp_ResponseCode");

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (status.equals("00")) {
			List<Cart> cartList = cartService.getAllItemInCart(username);
			Order order = orderService.createOrder(Integer.valueOf(orderID), "Đã xử lý", "Chuyển khoản bằng VNPay",
					Double.valueOf(amount), customerService.findByUsername(username));

			cartList.forEach(cart -> {
				Random random = new Random();
				int idOrderDeatail = 100000 + random.nextInt(900000);

				Random randomKey = new Random();
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 5; j++) {
						sb.append(CHARACTERS.charAt(randomKey.nextInt(CHARACTERS.length())));
					}
					if (i < 2)
						sb.append('-');
				}

				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setId(idOrderDeatail);
				orderDetail.setPrice(cart.getPrice());
				orderDetail.setQuantity(cart.getQuantity());
				orderDetail.setDiscount(cart.getDiscount());
				orderDetail.setKeyProduct(sb.toString());

				Product product = productService.findById(cart.getProductID()).get();

				orderDetail.setProduct(product);
				orderDetail.setOrder(order);
				orderDetailService.createOrderDetail(orderDetail);

				int newQuantity = product.getQuantity() - cart.getQuantity();
				if (newQuantity >= 0) {
					System.out.println(product.getQuantity());
					product.setQuantity(newQuantity);
					productService.update(product);
				} else {
					throw new IllegalArgumentException(product.getName());
				}
			});

			cartService.deleteAllItemInCart();
			return ResponseEntity.status(302).header("Location", "/paymentSuccess").build();
		} else {
			return ResponseEntity.status(302).header("Location", "/paymentFail").build();
		}
	}

	@PostMapping("/pay")
	public ResponseEntity<String> getPay(@RequestBody Map<String, Object> requestData, HttpServletRequest request)
			throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		String username = (String) requestData.get("username");
		session.setAttribute("username", username);

		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";
		double totalPrice = Double.valueOf(requestData.get("totalPrice").toString());
		int amount = (int) (totalPrice * 100);
		String bankCode = "NCB";

		String vnp_TxnRef = VNPayConfig.getRandomNumber(6);
		String vnp_IpAddr = "127.0.0.1";

		String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		if (bankCode != null && !bankCode.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCode);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);

		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

		return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(paymentUrl);
	}

}
