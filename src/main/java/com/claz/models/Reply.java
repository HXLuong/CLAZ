package com.claz.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reply")
public class Reply implements Serializable {
	@Id
	@Column(name = "Reply_ID", updatable = false, nullable = false)
	private int id;

	@Column(name = "Content")
	private String content;

	@Column(name = "created_at")
	LocalDateTime created_at = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "Comment_ID")
	private Comment comment;

	@ManyToOne
	@JoinColumn(name = "Username")
	private Customer customer;

}