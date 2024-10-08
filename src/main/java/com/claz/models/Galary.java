package com.claz.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "galary")
public class Galary {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	int id;
	String image;

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
}
