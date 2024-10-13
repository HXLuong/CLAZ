package com.claz.models;

import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	@Column(name = "Username", updatable = false, nullable = false)
	String username;
	String fullname;
    String password;
    String email;
    String phone;
    String image;
    boolean gender;
    @Temporal(TemporalType.DATE)
    @Column(name = "Created_at")
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
}
