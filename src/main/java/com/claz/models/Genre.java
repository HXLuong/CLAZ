package com.claz.models;

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
@Table(name = "genre")
public class Genre {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	int id;
	String name;
	String decription;

	@JsonIgnore
	@OneToMany(mappedBy = "genre")
	List<GenreProduct> genreProducts;
}
