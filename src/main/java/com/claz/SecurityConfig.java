package com.claz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Arrays;

import com.claz.models.Customer;
import com.claz.services.CustomerService;
import com.claz.models.Customer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomerService customerService;
	@Autowired
	private BCryptPasswordEncoder pe;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Customer user = customerService.findByUsername(username);
				String pass = user.getPassword();
//				String role = user.getRole().toString();
				return User.withUsername(username).password(pass).roles("Customer").accountExpired(false)
						.accountLocked(false).credentialsExpired(false).disabled(false) // Các tham số khác nếu cần
						.build();
			} catch (Exception e) {
				throw new UsernameNotFoundException(username + "Not Found");
			}
		});
	}

	public void loginFromOauth2(OAuth2AuthenticationToken oauth2) {
		String email = oauth2.getPrincipal().getAttribute("email");
		String pass = Long.toHexString(System.currentTimeMillis());

		UserDetails user = User.withUsername(email).password(pe.encode(pass)).roles("GUEST").build();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable();

		http.authorizeRequests().antMatchers("/order/**").authenticated().antMatchers("/admin/**").hasAnyRole("ADMIN")
				.antMatchers("/cart-index").hasAnyRole("ADMIN", "USER", "GUEST").anyRequest().permitAll();

		http.formLogin().loginPage("/login").loginProcessingUrl("/security/login")
				.defaultSuccessUrl("/login-success", false).failureUrl("/security/login/error");

		http.rememberMe().tokenValiditySeconds(86400);

		http.exceptionHandling().accessDeniedPage("/unauthoried");
		http.logout().logoutUrl("/security/logoff").logoutSuccessUrl("/logoff-success");

		http.oauth2Login().loginPage("/security/login/form").defaultSuccessUrl("/security/login/success", true)
				.failureUrl("/security/login/error").authorizationEndpoint().baseUri("/oauth2/authorization");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS);
	}



}
