package com.claz.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.claz.VNPayConfig;
import com.claz.jwt.EmailService;
import com.claz.models.Cart;
import com.claz.models.Category;
import com.claz.models.Customer;
import com.claz.models.Order;
import com.claz.models.OrderDetail;
import com.claz.models.Product;
import com.claz.models.momo.PaymentResponse;
import com.claz.models.momo.RequestType;
import com.claz.momoConfig.CustomerEnvironment;
import com.claz.serviceImpls.CreateOrderMoMo;
import com.claz.services.CartService;
import com.claz.services.CategoryService;
import com.claz.services.CustomerService;
import com.claz.services.OrderDetailService;
import com.claz.services.OrderService;
import com.claz.services.ProductService;
import com.claz.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@Autowired
	EmailService emailService;

	@Autowired
	private CustomerEnvironment environment;

	@Autowired
	private CreateOrderMoMo createOrderMoMo;

	@Autowired
	private CategoryService categoryService;

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

	// Payment VNPay
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
					product.setPurchases(product.getPurchases() + cart.getQuantity());
					productService.update(product);
				} else {
					throw new IllegalArgumentException(product.getName());
				}

				// send mail
				Customer customer = customerService.findByUsername(username);
				sendConfirmationEmail(customer.getEmail(), orderID, customer.getFullname(), order.getCreated_at(),
						customer.getPhone(), order.getStatus(), orderDetail.getProduct().getImage(),
						orderDetail.getProduct().getName(), orderDetail.getKeyProduct(), orderDetail.getPrice(),
						orderDetail.getQuantity(), orderDetail.getDiscount(), order.getAmount());
			});

			cartService.deleteAllItemInCart();

			return ResponseEntity.status(302).header("Location", "/paymentSuccess").build();
		} else {
			return ResponseEntity.status(302).header("Location", "/paymentFail").build();
		}
	}

	// Payment MoMo
	@PostMapping("/payMoMo")
	public PaymentResponse paymentMoMo(@RequestBody Map<String, Object> requestData, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String username = (String) requestData.get("username");
		session.setAttribute("username", username);
		LogUtils.init();
		String requestId = String.valueOf(System.currentTimeMillis());
		String orderId = VNPayConfig.getRandomNumber(6);
		Long transId = 2L;
		double totalPrice = Double.valueOf(requestData.get("totalPrice").toString());
		long amount = (long) (totalPrice * 100);
		String partnerClientId = "partnerClientId";
		String orderInfo = "Pay With MoMo";
		String returnUrl = "http://localhost:8080/rest/carts/paymentMoMoSuccess";
		String notifyURL = "http://google.com.vn";
		CustomerEnvironment environment = CustomerEnvironment.selectEnv("dev");
		PaymentResponse captureWalletMoMoResponse = createOrderMoMo.process(environment, orderId, requestId,
				Long.toString(amount / 100), orderInfo, returnUrl, notifyURL, "", RequestType.PAY_WITH_ATM,
				Boolean.TRUE);
		return captureWalletMoMoResponse;
	}

	@GetMapping("/paymentMoMoSuccess")
	public ResponseEntity<Void> paymentMoMoSuccess(@RequestParam("orderId") String orderId,
			@RequestParam("amount") String amount, @RequestParam("resultCode") String resultCode,
			@RequestParam("message") String message, @RequestParam("transId") String transId,
			HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if ("0".equals(resultCode)) { // Nếu resultCode == 0 thì thanh toán thành công

			List<Cart> cartList = cartService.getAllItemInCart(username);
			Order order = orderService.createOrder(Integer.valueOf(orderId), "Đã xử lý", "Chuyển khoản bằng MoMo",
					Double.valueOf(amount) * 100, customerService.findByUsername(username));

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
					product.setPurchases(product.getPurchases() + cart.getQuantity());
					productService.update(product);
				} else {
					throw new IllegalArgumentException(product.getName());
				}

				// send mail
				Customer customer = customerService.findByUsername(username);
				sendConfirmationEmail(customer.getEmail(), orderId, customer.getFullname(), order.getCreated_at(),
						customer.getPhone(), order.getStatus(), orderDetail.getProduct().getImage(),
						orderDetail.getProduct().getName(), orderDetail.getKeyProduct(), orderDetail.getPrice(),
						orderDetail.getQuantity(), orderDetail.getDiscount(), order.getAmount());
			});

			cartService.deleteAllItemInCart();

			return ResponseEntity.status(302).header("Location", "/paymentSuccess").build();
		} else {
			return ResponseEntity.status(302).header("Location", "/paymentFail").build();
		}
	}

	// gửi mail khi thanh toán xong
	private void sendConfirmationEmail(String email, String orderID, String fullname, LocalDateTime created_at,
			String phone, String status, String image, String nameProduct, String keyProduct, double price,
			int quantity, double discount, double amount) {
		try {

			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

			double totalAmount = 0.0;
			double lineTotal = Math.ceil(price * (1 - discount / 100) / 5000) * 5000;
			totalAmount += lineTotal * quantity;

			String subject = "CLAZ Shop - Đơn hàng mới " + orderID;
			String body = "<html>" + "<head><title>Xác nhận đơn hàng</title></head>"
					+ "<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0;\">\r\n"
					+ "    <div style=\"max-width: 600px; margin: auto; background-color: #f3f3f3; padding: 20px;\">\r\n"
					+ "        <div style=\"text-align: center;\">\r\n"
					+ "            <img src=\"http://localhost:8080/images/logo2.png\" alt=\"CLAZ Shop\"\r\n"
					+ "                style=\"width: 100%; max-width: 200px; margin: 20px 0;\">\r\n"
					+ "        </div>\r\n"
					+ "        <div style=\"text-align: center; background-color: #0149b5; padding: 30px; color: white;\">\r\n"
					+ "            <h1 style=\"margin: 10px;\">Đơn hàng mới #" + orderID + "</h1>\r\n"
					+ "            <span style=\"color: #a3bfea;\">Cảm ơn bạn đã quan tâm sản phẩm <br> của CLAZ Shop.</span>\r\n"
					+ "        </div>\r\n" + "        <div style=\"background-color: white; padding: 20px;\">\r\n"
					+ "            <h2 style=\"margin-top: 0;\">Chào " + fullname + ",</h2>\r\n"
					+ "            <p style=\"margin: 10px 0;\">Đơn hàng của bạn đã được nhận và sẽ được xử lý ngay khi bạn xác nhận thanh toán.\r\n"
					+ "            </p>\r\n"
					+ "            <span>Để xem chi tiết đơn hàng của mình tại CLAZ Shop, bạn có thể <a\r\n"
					+ "                    style=\"text-decoration: none; font-weight: bold;\"\r\n"
					+ "                    href=\"http://localhost:8080/detail_profile?id=" + orderID
					+ "\">nhấn vào đây</a>.</span>\r\n"
					+ "            <h2 style=\"margin: 19px 0;\">Thông tin đơn hàng #" + orderID + "</h2>\r\n"
					+ "            <p style=\"margin: 10px 0;\">Ngày mua đơn hàng " + formatter.format(created_at)
					+ "</p>\r\n"
					+ "            <div style=\"display: flex; flex-wrap: wrap; justify-content: space-between; margin-bottom: 20px;\">\r\n"
					+ "                <div style=\"flex: 1; margin-right: 10px;\">\r\n"
					+ "                    <p>Khách hàng: <strong>" + fullname + "</strong></p>\r\n"
					+ "                    <p>Email: <strong>" + email + "</strong></p>\r\n"
					+ "                    <p style=\"margin-bottom: 0;\">Số điện thoại: <strong>" + phone
					+ "</strong></p>\r\n" + "                </div>\r\n"
					+ "                <div style=\"flex: 1;\">\r\n" + "                    <p>Tình trạng: <strong>"
					+ status + "</strong></p>\r\n" + "                    <p>Email nhận sản phẩm: <strong>" + email
					+ "</strong></p>\r\n" + "                </div>\r\n" + "            </div>\r\n"
					+ "            <h2>Chi tiết đơn hàng #" + orderID + "</h2>\r\n"
					+ "            <div style=\"display: flex; margin-top: 20px; flex-wrap: wrap; justify-content: space-between;\">\r\n"
					+ "                <img src=\"./images/" + image + "\" alt=\"PRODUCT_NAME\"\r\n"
					+ "                    style=\"width: 100%; max-width: 200px; height: 100px; object-fit: cover; margin: 0 10px 10px 10px;\">\r\n"
					+ "                <div style=\"flex: 1; min-width: 200px; margin: 0 10px;\">\r\n"
					+ "                    <span style=\"font-weight: bold; font-size: 18px; display: block;\">"
					+ nameProduct + "</span>\r\n" + "                    <p\r\n"
					+ "                        style=\"border: 1px solid #639cf2; font-size: 18px; padding: 10px; text-align: center; margin: 10px 0;\">\r\n"
					+ "                        " + keyProduct + "\r\n" + "                    </p>\r\n"
					+ "                    <a style=\"text-decoration: none; font-weight: bold;\" href=\"\">Hướng dẫn nhập key game</a><br>\r\n"
					+ "                    <p style=\"font-size: 18px; color: #878787; margin: 10px 0;\">Số lượng: "
					+ quantity + "</p>\r\n" + "                </div>\r\n"
					+ "                <div style=\"text-align: start; flex: 0 0 auto; min-width: 120px; margin-left: 10px;\">\r\n"
					+ "                    <span style=\"font-weight: bold; font-size: 18px;\">"
					+ currencyFormat.format(totalAmount) + "</span><br>\r\n"
					+ "                    <span style=\"font-size: 18px; color: #878787;\">Đơn giá: "
					+ currencyFormat.format(lineTotal) + "</span>\r\n" + "                </div>\r\n"
					+ "            </div>\r\n" + "\r\n" + "            <hr>\r\n"
					+ "            <div style=\"text-align: end;\">\r\n"
					+ "                <p style=\"font-size: 18px;\">Tổng giá trị sản phẩm: <strong style=\"font-size: 18px;\">"
					+ currencyFormat.format(amount / 100) + "</strong></p>\r\n" + "            </div>\r\n"
					+ "            <div style=\"text-align: center; margin: 50px 0 30px;\">\r\n"
					+ "                <a style=\"background-color: #2679f2; color: white; text-decoration: none; padding: 20px; font-size: 18px;\"\r\n"
					+ "                    href=\"http://localhost:8080/\">Tiếp tục mua sắm</a>\r\n"
					+ "            </div>\r\n" + "        </div>\r\n" + "    </div>\r\n" + "</body>" + "</html>";

			// Send email using the EmailService
			emailService.sendSimpleEmail(email, subject, body);
		} catch (Exception e) {
			e.printStackTrace();
			// Handle exception appropriately
		}
	}
}
