package com.claz.models;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {

	@Id
	@Column(name = "username", updatable = false, nullable = false)
	String username;
	String fullname;
	String password;
	String email;
	String phone;
	String image;
	boolean gender;
	boolean role;
	@Column(name = "created_at")
	LocalDateTime created_at = LocalDateTime.now();
}
