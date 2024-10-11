package com.claz.models;

import javax.persistence.*;
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
