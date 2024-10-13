package com.claz.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @Temporal(TemporalType.DATE)
    @Column(name = "Created_at")
    Date Created_at = new Date();
    
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
