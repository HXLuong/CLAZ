package com.claz.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {

	@Id
	@Column(name = "OrderDetail_ID", updatable = false, nullable = false)
	int id;
	Double price;
	int quantity;
	Double discount;
	String keyProduct;

	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
}
