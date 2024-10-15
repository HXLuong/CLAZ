package com.claz.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.claz.jwt.EmailService;
import com.claz.jwt.JwtProvider;
import com.claz.jwt.JwtTokenFilter;
import com.claz.models.Customer;
import com.claz.models.ResetPassword;
import com.claz.models.SendMail;
import com.claz.services.CustomerService;

@RestController
@CrossOrigin("*")
public class ForgotPassWord {

	@Autowired
	CustomerService customerService;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	JwtTokenFilter filter;

	@Autowired
	EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/send/mail")
	public ResponseEntity<?> sendMail(@RequestBody SendMail mail) {
		if (mail.getSendmail() == null) {
			return new ResponseEntity<>("Email không hợp lệ.", HttpStatus.BAD_REQUEST);
		}
		try {
			Customer customer = customerService.findByEmail(mail.getSendmail())
					.orElseThrow(() -> new UsernameNotFoundException("Khách hàng không tồn tại"));
			String token = jwtProvider.generateToken(customer.getUsername());
			String linkReset = "http://localhost:8080/login?token=" + token;
			emailService.sendSimpleEmail(mail.getSendmail(), "Thay đổi mật khẩu", linkReset);
			return new ResponseEntity<>(linkReset, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Có lỗi xảy ra.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/reset/password")
	public ResponseEntity<?> resetPassword(HttpServletRequest request,
			@Validated @RequestBody ResetPassword resetPassword) {
		String jwt = filter.getJwt(request);

		if (!jwtProvider.validateJwtToken(jwt)) {
			return new ResponseEntity<>("Token không hợp lệ", HttpStatus.UNAUTHORIZED);
		}

		String username = jwtProvider.getUserNameFromToken(jwt);

		try {
			Customer customer = customerService.findByUsername(username);
			customer.setPassword(passwordEncoder.encode(resetPassword.getNewpass()));
			customerService.createAccount(customer);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UsernameNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
