package com.claz.controllers;

import com.claz.models.Category;
import com.claz.models.Comment;
import com.claz.models.Galary;
import com.claz.models.Genre;
import com.claz.models.GenreProduct;
import com.claz.models.Order;
import com.claz.models.Product;
import com.claz.models.Reply;
import com.claz.models.Slide;
import com.claz.services.CategoryService;
import com.claz.services.CommentService;
import com.claz.services.CustomerService;
import com.claz.services.GalaryService;
import com.claz.services.GenreProductService;
import com.claz.services.GenreService;
import com.claz.services.OrderDetailService;
import com.claz.services.OrderService;
import com.claz.services.ProductService;
import com.claz.services.SlideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SlideService slideService;

	@Autowired
	private GenreProductService genreProductService;

	@Autowired
	private GalaryService galaryService;

	@Autowired
	private OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@Autowired
	CommentService commentService;

	@Autowired
	CustomerService customerService;

	@Autowired
	private GenreService genreService;

	@RequestMapping("/")
	public String index(HttpSession session, Model model, @RequestParam(defaultValue = "8") int productsMore,
			@RequestParam(defaultValue = "8") int productsBest, @RequestParam(defaultValue = "8") int productsSteam,
			@RequestParam(defaultValue = "8") int productsStudy, @RequestParam(defaultValue = "8") int productsWork) {
		if (session.getAttribute("searchProduct") == null) {
			List<Product> prHot = productService.findByHot();
			session.setAttribute("products", prHot);
			List<Product> prBestSeller = productService.findByBestSeller(10);
			session.setAttribute("productBestSellers", prBestSeller);
			List<Product> pr2 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 4).toList();
			session.setAttribute("gamesteam", pr2);
			List<Product> pr3 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 2).toList();
			session.setAttribute("lamviec", pr3);
			List<Product> pr4 = productService.findAll().stream().filter(e -> e.getCategory().getId() == 3).toList();
			session.setAttribute("hoctap", pr4);
		}

		List<Category> cate = categoryService.findAll();
		model.addAttribute("cates", cate);

		List<Slide> slide = slideService.findAll();
		session.setAttribute("slides", slide);

		session.setAttribute("productsMore", productsMore);
		session.setAttribute("productsBest", productsBest);
		session.setAttribute("productsSteam", productsSteam);
		session.setAttribute("productsStudy", productsStudy);
		session.setAttribute("productsWork", productsWork);
		model.addAttribute("isHome", true);
		session.setAttribute("page", "component/home");
		return "index";
	}

	@RequestMapping("/{id}")
	public String detailProduct(HttpSession session, Model model, @PathVariable("id") int id) {
		Product item = productService.finById(id);
		List<GenreProduct> genreProducts = genreProductService.findAllByproductId(id);
		List<Galary> galaries = galaryService.findAllByproductId(id);
		List<Category> cate = categoryService.findAll();
		model.addAttribute("cates", cate);
		session.setAttribute("genreProducts", genreProducts);
		session.setAttribute("galaries", galaries);
		session.setAttribute("item", item);
		session.setAttribute("productId", item.getId());
		session.setAttribute("page", "/detailProduct/detailProduct");

		String itemName = item.getName();
		if (itemName != null) {
			int spaceIndex = itemName.indexOf(" ");
			if (spaceIndex != -1) {
				itemName = itemName.substring(0, spaceIndex);
			}
		}
		session.setAttribute("productDisplayCount", 8);
		session.setAttribute("searchProduct", productService.findBySearch(itemName));
		return "index";
	}

	@GetMapping("/search")
	public String searchProductFilter(@RequestParam(required = false) String search, HttpSession session, Model model,
			@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer genreId,
			@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice,
			@RequestParam(required = false) String sort) {
		if (search == null || search.trim().isEmpty()) {
			search = "";
		}

		if (!search.isEmpty()) {
			session.setAttribute("searchProduct", productService.findBySearch(search));
		} else {
			List<Product> products = productService.findProducts(categoryId, genreId, minPrice, maxPrice, sort);
			session.setAttribute("searchProduct", products);
		}
		List<Genre> genres = genreService.findAll();
		List<Category> cates = categoryService.findAll();

		session.setAttribute("cates", cates);
		model.addAttribute("cates", cates);
		session.setAttribute("genres", genres);

		session.setAttribute("page", "search/search");
		return "index";
	}

	@RequestMapping("/cart-index")
	public String cart(HttpSession session, Model model) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		session.setAttribute("page", "/cart/cart-index");
		return "index";
	}

	@RequestMapping("/paymentSuccess")
	public String paymentSuccess(HttpSession session, HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		session.setAttribute("customers", customerService.findByUsername(username));
		session.setAttribute("page", "/cart/paymentSuccess");
		return "index";
	}

	@RequestMapping("/paymentFail")
	public String paymentFail(HttpSession session, Model model) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		session.setAttribute("page", "/cart/paymentFail");
		return "index";
	}

	@GetMapping("/category")
	public String danhmuc(Model model, @RequestParam(required = false) Integer id, HttpSession session,
			@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer genreId,
			@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice,
			@RequestParam(required = false) String sort) {
		List<Product> products;

		if (categoryId != null || genreId != null) {
			products = productService.findProducts(categoryId, genreId, minPrice, maxPrice, sort);
		} else if (id != null) {
			products = productService.findAllByCategoryId(id);
		} else {
			products = productService.findProducts(categoryId, genreId, minPrice, maxPrice, sort);
		}

		List<Genre> genres = genreService.findAll();
		List<Category> cates = categoryService.findAll();

		session.setAttribute("cates", cates);
		model.addAttribute("cates", cates);
		session.setAttribute("genres", genres);
		session.setAttribute("searchProdut", products);
		model.addAttribute("title", "Danh Mục Sản Phẩm");
		session.setAttribute("page", "/category/category");
		return "index";
	}

	@RequestMapping("/prHot")
	public String prHot(Model model, HttpSession session) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		List<Product> prHot = productService.findByHot();
		session.setAttribute("searchProdut", prHot);
		model.addAttribute("title", "Sản Phẩm Nổi Bật");
		session.setAttribute("page", "/category/category");
		return "index";
	}

	@RequestMapping("/prBestSeller")
	public String prBestSeller(Model model, HttpSession session) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		List<Product> prBestSeller = productService.findByBestSeller(10);
		session.setAttribute("searchProdut", prBestSeller);
		model.addAttribute("title", "Sản Phẩm Bán Chạy Nhất");
		session.setAttribute("page", "/category/category");
		return "index";
	}

	@RequestMapping("/prSale")
	public String prSale(Model model, HttpSession session) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		List<Product> prSale = productService.findProductsWithDiscount();
		session.setAttribute("searchProdut", prSale);
		model.addAttribute("title", "Sản Phẩm Khuyến mãi");
		session.setAttribute("page", "/category/category");
		return "index";
	}

	@RequestMapping("/account")
	public String upaccount(HttpSession session, Model model) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		session.setAttribute("page", "/update_profile/account_profile");
		return "index";
	}

	@RequestMapping("/password")
	public String uppass(HttpSession session, Model model) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		session.setAttribute("page", "/update_profile/password_profile");
		return "index";
	}

	@RequestMapping("/order")
	public String donhang(HttpSession session, HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		List<Order> orders = orderService.findByUsername(username);
		session.setAttribute("orders", orders);
		orders.sort(Comparator.comparing(Order::getCreated_at).reversed());
		session.setAttribute("page", "/update_profile/order_profile");
		return "index";
	}

	@RequestMapping("/filterOrder")
	public String filterOrder(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(required = false) Integer orderId, @RequestParam(required = false) Double amountFrom,
			@RequestParam(required = false) Double amountTo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		String username = request.getRemoteUser();
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);

		LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
		LocalDateTime toDateTime = (toDate != null) ? toDate.atTime(23, 59, 59) : null;

		boolean isFilterApplied = orderId != null || (amountFrom != null && amountTo != null) || fromDateTime != null
				|| toDateTime != null;

		if (isFilterApplied) {
			session.setAttribute("orders",
					orderService.filterOrders(orderId, (amountFrom != null ? amountFrom * 100 : null),
							(amountTo != null ? amountTo * 100 : null), fromDateTime, toDateTime, username));
		} else {
			session.setAttribute("orders", orderService.findByUsername(username));
		}

		session.setAttribute("page", "/update_profile/order_profile");
		return "index";
	}

	@RequestMapping("/detail_profile")
	public String detail_profile(HttpSession session, @RequestParam("id") int id, Model model) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		session.setAttribute("order", orderService.findById(id));
		session.setAttribute("page", "/update_profile/detail_profile");
		return "index";
	}

	@RequestMapping("/payment")
	public String giaodich(HttpSession session, HttpServletRequest request, Model model) {
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		String username = request.getRemoteUser();
		List<Order> orders = orderService.findByUsername(username);
		session.setAttribute("orders", orders);
		orders.sort(Comparator.comparing(Order::getCreated_at).reversed());
		session.setAttribute("page", "/update_profile/payment_profile");
		return "index";
	}

	@RequestMapping("/filterPayment")
	public String filterPayment(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(required = false) String paymentMethod, @RequestParam(required = false) Double amountFrom,
			@RequestParam(required = false) Double amountTo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		String username = request.getRemoteUser();
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);

		LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
		LocalDateTime toDateTime = (toDate != null) ? toDate.atTime(23, 59, 59) : null;

		boolean isFilterApplied = paymentMethod != null || (amountFrom != null && amountTo != null)
				|| fromDateTime != null || toDateTime != null;

		if (isFilterApplied) {
			session.setAttribute("orders",
					orderService.filterPayments(paymentMethod, (amountFrom != null ? amountFrom * 100 : null),
							(amountTo != null ? amountTo * 100 : null), fromDateTime, toDateTime, username));
		} else {
			session.setAttribute("orders", orderService.findByUsername(username));
		}

		session.setAttribute("page", "/update_profile/payment_profile");
		return "index";
	}

	@RequestMapping("/comment")
	public String binhluan(HttpSession session, HttpServletRequest request, Model model) {
		String username = request.getRemoteUser();
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);
		List<Comment> comments = commentService.findByUsername(username);
		List<Reply> replies = commentService.findByUsernameReply(username);
		session.setAttribute("comments", comments);
		session.setAttribute("replies", replies);
		comments.sort(Comparator.comparing(Comment::getCreated_at).reversed());
		replies.sort(Comparator.comparing(Reply::getCreated_at).reversed());
		session.setAttribute("page", "/update_profile/comment_profile");
		return "index";
	}

	@RequestMapping("/filterComment")
	public String filterComment(HttpSession session, HttpServletRequest request, Model model,
			@RequestParam(required = false) String content,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		String username = request.getRemoteUser();
		List<Category> cates = categoryService.findAll();
		model.addAttribute("cates", cates);

		LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
		LocalDateTime toDateTime = (toDate != null) ? toDate.atTime(23, 59, 59) : null;

		boolean isFilterApplied = content != null || fromDateTime != null || toDateTime != null;

		if (isFilterApplied) {
			session.setAttribute("comments",
					commentService.filterComments(content, fromDateTime, toDateTime, username));
			session.setAttribute("replies", commentService.filterReplies(content, fromDateTime, toDateTime, username));
		} else {
			session.setAttribute("comments", commentService.findByUsername(username));
			session.setAttribute("replies", commentService.findByUsernameReply(username));
		}

		session.setAttribute("page", "/update_profile/comment_profile");
		return "index";
	}

	@RequestMapping("/cart_instruct")
	public String cart_instruct(Model model) {
		return "/instruct/cart_instruct";
	}

	@RequestMapping("/payment_instruct")
	public String payment_instruct(Model model) {
		return "/instruct/payment_instruct";
	}

	@RequestMapping("/vnpay_instruct")
	public String vnpay_instruct(Model model) {
		return "/instruct/payment_vnpay_instruct";
	}

	@RequestMapping("/momo_instruct")
	public String momo_instruct(Model model) {
		return "/instruct/payment_momo_instruct";
	}

	@RequestMapping("/account_instruct")
	public String account_instruct(Model model) {
		return "/instruct/account_instruct";
	}

	@RequestMapping("/buymain_instruct")
	public String buymain_instruct(Model model) {
		return "/instruct/buymain_instruct";
	}

	@RequestMapping("/bonus_instruct")
	public String bonus_instruct(Model model) {
		return "/instruct/bonus_instruct";
	}

	@RequestMapping("/about_instruct")
	public String about_instruct(Model model) {
		return "/instruct/about_instruct";
	}

	@RequestMapping("/security_instruct")
	public String security_instruct(Model model) {
		return "/instruct/security_instruct";
	}

	@RequestMapping("/signup_instruct")
	public String signup_instruct(Model model) {
		return "/instruct/signup_instruct";
	}

	@RequestMapping("/changepass")
	public String changPass(Model model) {
		return "/login/changePass";
	}

}
