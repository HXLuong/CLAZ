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
@Table(name = "rating")
public class Rating {
	@Id
	@Column(name = "Rating_ID", updatable = false, nullable = false)
	int id;
	int number_Stars;
<<<<<<< HEAD
	@Column(name = "created_at")
	LocalDateTime createdAt;
=======

	@Column(name = "created_at")
	LocalDateTime created_at = LocalDateTime.now();
>>>>>>> abb2f9bd68225994a1d661327f725013cc17c6ac

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;

	@ManyToOne
	@JoinColumn(name = "username")
	Customer customer;
}
