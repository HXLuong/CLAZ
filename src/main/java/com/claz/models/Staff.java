package com.claz.models;

import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    Date created_at = new Date();

}
