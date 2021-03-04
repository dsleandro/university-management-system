package com.dsleandro.university.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "professors")
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, max = 30)
	@Pattern(regexp = "[a-zA-Z -'.]*")
	@NotBlank
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 2, max = 40)
	@Pattern(regexp = "[a-zA-Z -'.]*")
	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@Size(min = 4, max = 8)
	@Pattern(regexp = "[0-9]*")
	@NotBlank
	@Column(name = "dni")
	private String dni;

	@Column(name = "active")
	private String active;

}
