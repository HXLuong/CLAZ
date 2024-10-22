package com.claz.models;

import java.time.LocalDateTime;
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
	@Column(name = "Product_ID", updatable = false, nullable = false)
	int id;
	String Name;
	String Image;
	Double Price;
	int Quantity;
	String Decription;
	Double Discount;
	int Hot;
	Double Total_Pay;
	int Total_Rating;
	int Total_Stars;

//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_at")
//	Date Created_at = new Date();
	LocalDateTime Created_at;
	@ManyToOne
	@JoinColumn(name = "Category_ID")
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
