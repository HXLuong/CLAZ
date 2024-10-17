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
	@Column(name = "Galary_ID", updatable = false, nullable = false)
	int id;
	String image;

	@ManyToOne
	@JoinColumn(name = "Product_ID")
	Product product;
}
