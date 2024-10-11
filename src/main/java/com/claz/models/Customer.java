package com.claz.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@Column(name = "username")
	String username;
	String fullname;
	String password;
	String email;
	String phone;
	String image;
	boolean gender;
	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	Date created_at = new Date();

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	List<Cart> cart;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	List<Comment> comment;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	List<Rating> rating;

	@PrePersist
	public void prePersist() {
		if (this.username == null) {
			this.username = RandomStringUtils.randomAlphanumeric(8);
		}
	}

}
