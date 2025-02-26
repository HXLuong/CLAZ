package com.claz.models;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Customer implements Serializable {

	@Id
	@Column(name = "Username", updatable = false, nullable = false)
	String username;
	String fullname;
    String password;
    String email;
    String phone;
    String image;
    boolean gender;
    @Column(name = "Created_at")
	  LocalDateTime created_at = LocalDateTime.now();
    
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
