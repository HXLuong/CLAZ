package com.claz.models;

import java.util.List;

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
	int productID;
	Double discount;

	@ManyToOne
	@JoinColumn(name = "username")
	Customer customer;
}
