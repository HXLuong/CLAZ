package com.claz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.claz.models.Staff;
import com.claz.services.CustomerService;
import com.claz.services.StaffService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomerService customerService;

	@Autowired
	private StaffService StaffService;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Staff staff = StaffService.findByUsername(username).orElse(null);
				if (staff != null) {
					String role = staff.isRole() ? "ADMIN" : "USER";
					return User.withUsername(username).password(staff.getPassword()).roles(role).accountExpired(false)
							.accountLocked(false).credentialsExpired(false).disabled(false).build();
				}
			} catch (Exception e) {
				throw new UsernameNotFoundException("Staff " + username + " not found");
			}

			try {
				Customer user = customerService.findByUsername(username);
				String pass = user.getPassword();
				return User.withUsername(username).password(pass).roles("CUSTOMER").accountExpired(false)
						.accountLocked(false).credentialsExpired(false).disabled(false).build();
			} catch (Exception e) {
				throw new UsernameNotFoundException("Customer " + username + " not found");
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

		http.authorizeRequests().antMatchers("/admin**").hasAnyRole("ADMIN", "USER").antMatchers("/order/**")
				.authenticated().antMatchers("/cart-index").hasRole("CUSTOMER").anyRequest().permitAll();

		http.formLogin().loginPage("/login").loginProcessingUrl("/security/login")
				.defaultSuccessUrl("/login-success", true).failureUrl("/security/login/error");

		http.rememberMe().tokenValiditySeconds(86400);

		http.logout().logoutUrl("/security/logoff").logoutSuccessUrl("/logoff-success");

		http.oauth2Login().loginPage("/security/login/form").defaultSuccessUrl("/security/login/success", true)
				.failureUrl("/security/login/error").authorizationEndpoint().baseUri("/oauth2/authorization");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS);
	}

}