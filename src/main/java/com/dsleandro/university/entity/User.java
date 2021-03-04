package com.dsleandro.university.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Size(min = 2, max = 30)
	@Pattern(regexp = "[a-zA-Z -']*")
	@NotBlank
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 2, max = 40)
	@Pattern(regexp = "[a-zA-Z -']*")
	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@Size(min = 4, max = 8)
	@Pattern(regexp = "[0-9]*")
	@NotBlank
	@Column(name = "dni")
	private String dni;

	@Size(min = 4, max = 255)
	@NotBlank
	@Column(name = "file_number")
	private String password; // student file number and admin password

	@ManyToOne
    @JoinColumn(name = "role")
	private Role role;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_subject", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "subject_id", referencedColumnName = "id"))
	private Set<Subject> subjects;

	@Column(name = "active")
	private boolean active;

}
