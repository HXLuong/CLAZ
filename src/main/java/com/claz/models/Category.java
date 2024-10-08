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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category {
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	int id;
	@Column(nullable = false, unique = true)
	String name;
	@Column(nullable = false, unique = true)
	String decription;

	@JsonIgnore
	@OneToMany(mappedBy = "category")
	List<Product> products;
}
