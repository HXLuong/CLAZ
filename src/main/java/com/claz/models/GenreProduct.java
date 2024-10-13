package com.claz.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre_product")
public class GenreProduct {
	@Id
	@Column(name = "GenreProduct_ID", updatable = false, nullable = false)
	int id;
	String name;

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;

	@ManyToOne
    @JoinColumn(name = "Genre_ID")
    Genre genre;

}
