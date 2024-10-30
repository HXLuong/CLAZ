package com.claz.jwt;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	private final String username = "thanhannguyen415@gmail.com";
	private final String password = "wrthnlvrufrxanwr";

	public void sendSimpleEmail(String to, String subject, String body) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(body, "text/html; charset=UTF-8");

			Transport.send(message);

			System.out.println("Email đã được gửi thành công đến " + to);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
