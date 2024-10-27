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
@Table(name = "orders")
public class Order {

	@Id
	@Column(name = "Order_ID", updatable = false, nullable = false)
	int id;
	String status;
	String paymentMethod;
	Double amount;
	@Column(name = "created_at")
	LocalDateTime created_at = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "username")
	Customer customer;

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetails;
}
