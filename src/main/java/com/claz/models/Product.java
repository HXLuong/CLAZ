package com.claz.models;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Product implements Serializable {

	@Id
	@Column(name = "Product_ID", updatable = false, nullable = false)
	int id;
	String Name;
	String Image;
	Double Price;
	int Quantity;
	String Decription;
	Double Discount;
	boolean Hot;
	int Purchases;
	Double Total_Pay;
	int Total_Rating;
	int Total_Stars;

	@Column(name = "Created_at")
	LocalDateTime created_at = LocalDateTime.now();
	
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
	List<Comment> comment;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Rating> rating;
	
}
