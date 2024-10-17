package com.claz.controllers;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.AssertingParty.Verification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claz.jwt.EmailService;
import com.claz.jwt.JwtProvider;
import com.claz.jwt.JwtTokenFilter;
import com.claz.models.Customer;
import com.claz.models.ErrorResponse;
import com.claz.models.ResetPassword;
import com.claz.models.SendMail;
import com.claz.models.SuccessResponse;
import com.claz.models.VerificationRequest;
import com.claz.services.CustomerService;

@RestController
@CrossOrigin("*")
public class ForgotPassWord {

//	@PostMapping("/send/mail")
//	public ResponseEntity<?> sendMail(@RequestBody SendMail mail) {
//		if (mail.getSendmail() == null) {
//			return new ResponseEntity<>("Email không hợp lệ.", HttpStatus.BAD_REQUEST);
//		}
//		try {
//			Customer customer = customerService.findByEmail(mail.getSendmail())
//					.orElseThrow(() -> new UsernameNotFoundException("Khách hàng không tồn tại"));
//			String token = jwtProvider.generateToken(customer.getUsername());
//			String linkReset = "http://localhost:8080/login?token=" + token;
//			emailService.sendSimpleEmail(mail.getSendmail(), "Thay đổi mật khẩu", linkReset);
//			return new ResponseEntity<>(linkReset, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("Có lỗi xảy ra.", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

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
	public ResponseEntity<?> sendMail(HttpServletRequest request, @RequestBody SendMail mail) {
		if (mail.getSendmail() == null || mail.getSendmail().isEmpty()) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Email không hợp lệ."));
		}
		try {
			Customer customer = customerService.findByEmail(mail.getSendmail())
					.orElseThrow(() -> new UsernameNotFoundException("Khách hàng không tồn tại"));
			String randomCode = generateRandomCode();
			request.getSession().setAttribute("email", mail.getSendmail());
			request.getSession().setAttribute("userId", customer.getUsername());
			request.getSession().setAttribute("code", randomCode);
			emailService.sendSimpleEmail(mail.getSendmail(), "Mã xác nhận thay đổi mật khẩu",
					"Mã xác nhận của bạn là: " + randomCode);
			return ResponseEntity.ok(new SuccessResponse("Mã xác nhận đã được gửi đến email của bạn.", randomCode));
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Email không tồn tại."));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Có lỗi xảy ra khi gửi email."));
		}
	}

	@PostMapping("/verify/code")
	public ResponseEntity<?> verifyCode(HttpServletRequest request,
			@RequestBody VerificationRequest verificationRequest) {
		String sessionEmail = (String) request.getSession().getAttribute("email");
		String sessionCode = (String) request.getSession().getAttribute("code");
		String userId = (String) request.getSession().getAttribute("userId");
		if (sessionEmail == null || sessionCode == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Không tìm thấy mã xác nhận."));
		}
		if (sessionEmail.equals(verificationRequest.getEmail()) && sessionCode.equals(verificationRequest.getCode())) {
			return ResponseEntity.ok(new SuccessResponse("Mã xác nhận chính xác.", userId));
		} else {
			return ResponseEntity.badRequest().body(new ErrorResponse("Mã xác nhận không chính xác."));
		}
	}

	private String generateRandomCode() {
		SecureRandom random = new SecureRandom();
		int code = 100000 + random.nextInt(900000);
		return String.valueOf(code);
	}

	@PostMapping("/reset/password")
	public ResponseEntity<?> resetPassword(HttpServletRequest request,
			@Validated @RequestBody ResetPassword resetPassword) {
		String userId = (String) request.getSession().getAttribute("userId");
		if (userId == null) {
			return ResponseEntity.badRequest().body(new ErrorResponse("Người dùng không được xác thực."));
		}
		try {
			Customer customer = customerService.findByUsername(userId);
			customer.setPassword(passwordEncoder.encode(resetPassword.getNewpass()));
			customerService.update(customer);
			return ResponseEntity.ok(new SuccessResponse("Mật khẩu đã được cập nhật thành công."));
		} catch (UsernameNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Khách hàng không tồn tại."));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("Có lỗi xảy ra khi cập nhật mật khẩu."));
		}
	}

	@GetMapping("/rest/customer/check-email")
	public ResponseEntity<Optional<Customer>> checkEmailExists(@RequestParam String email) {
		Optional<Customer> exists = customerService.findByEmail(email);
		return ResponseEntity.ok(exists);
	}

}
