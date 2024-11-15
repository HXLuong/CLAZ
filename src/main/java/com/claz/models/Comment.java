package com.claz.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment implements Serializable {
	@Id
	@Column(name = "Comment_ID", updatable = false, nullable = false)
	private int id;

	private String content;

	@Column(name = "created_at")
	LocalDateTime created_at = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "Product_ID")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "Username")
	private Customer customer;

}
