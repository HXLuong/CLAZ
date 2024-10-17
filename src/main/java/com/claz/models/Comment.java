package com.claz.models;

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
@Table(name = "comment")
public class Comment {
	@Id
	@Column(name = "Comment_ID", updatable = false, nullable = false)
	int id;
	String content;
	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	Date created_at = new Date();

	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;

	@ManyToOne
	@JoinColumn(name = "username")
	Customer customer;
}
