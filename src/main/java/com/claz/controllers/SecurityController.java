package com.claz.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claz.models.Customer;
import com.claz.repositories.CustomerRepository;
import com.claz.services.CustomerService;

@Controller
public class SecurityController {

	@Autowired
	CustomerService customerService;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	CustomerRepository dao;

	@RequestMapping("/login")
	public String login(Model model) {
		return "/login/login";
	}

	@RequestMapping("/login-success")
	public String success(Model model) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).orElse("");

		if (role.equals("ROLE_ADMIN") || role.equals("ROLE_USER")) {
			return "redirect:/admin";
		}

		Optional<Customer> customerOptional = customerService.getname(currentUsername);
		if (customerOptional.isPresent()) {
			String fullName = customerOptional.get().getFullname();
			model.addAttribute("fullName", fullName);
			System.out.println("Full name: " + fullName);
		} else {
			model.addAttribute("fullName", "Người dùng không xác định");
			System.out.println("Người dùng không xác định");
		}

		return "redirect:/";
	}

	@RequestMapping("/security/login/error")
	public String error(Model md) {
		md.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng");
		return "/login/login";
	}

	@GetMapping("/security/login/success")
	public String successOAuth2(OAuth2AuthenticationToken oauth2, Model model) {
		String provider = oauth2.getAuthorizedClientRegistrationId();
		String username = null;
		String email = oauth2.getPrincipal().getAttribute("email");
		String fullname = oauth2.getPrincipal().getAttribute("name");
		String image = null;
		if (provider.equals("google")) {
			username = oauth2.getPrincipal().getAttribute("sub");
//			image = oauth2.getPrincipal().getAttribute("avatar");
		} else if (provider.equals("facebook")) {
			username = oauth2.getPrincipal().getAttribute("id");
//			image = oauth2.getPrincipal().getAttribute("picture");
		}

		Optional<Customer> existingCustomer = customerService.getname(username);

		if (existingCustomer.isPresent()) {
			Customer customer = existingCustomer.get();
			model.addAttribute("fullName", customer.getFullname());
			System.out.println("Full name: " + customer.getFullname());
		} else {
			Customer customer = new Customer();
			customer.setEmail(email);
			customer.setFullname(fullname);
			customer.setUsername(username);
			customer.setImage("profile.jpg");
			customer.setPassword("");
			customerService.createAccount(customer);

			model.addAttribute("fullName", customer.getFullname());
			System.out.println("Full name: " + customer.getFullname());
		}
		return "redirect:/";
	}

	@RequestMapping("/logoff-success")
	public String logoff(Model md) {
		return "redirect:/";
	}

}
