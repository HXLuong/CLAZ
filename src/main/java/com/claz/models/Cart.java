package com.claz.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
	@Id
	@Column(name = "Cart_ID", updatable = false, nullable = false)
	int id;
	String name;
	Double price;
	int quantity;
	String image;

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;

	@ManyToOne
	@JoinColumn(name = "username")
	Customer customer;
}
