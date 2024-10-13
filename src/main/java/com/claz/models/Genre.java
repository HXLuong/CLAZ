package com.claz.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	@Column(name = "Genre_ID", updatable = false, nullable = false)
	int id;
	String name;
	String decription;
	
	@JsonIgnore
    @OneToMany(mappedBy = "genre")
    List<GenreProduct> genreProducts;
}
