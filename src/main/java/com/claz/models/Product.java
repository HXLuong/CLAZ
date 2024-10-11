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
@Table(name = "product")
public class Product {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	int id;
	String name;
	String image;
	Double price;
	int quantity;
	String description;
	Double discount;
	int hot;
	Double total_Pay;
	int total_Rating;
	int total_Stars;
	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	Date created_at = new Date();

	@ManyToOne
	@JoinColumn(name = "category_id")
	Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<GenreProduct> genreProducts;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Galary> galaries;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<OrderDetail> orderDetails;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Cart> cart;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Comment> comment;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Rating> rating;
}
