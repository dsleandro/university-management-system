package com.dsleandro.university.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // avoid @ToString due to error with Hibernate
@Entity
@Table(name = "roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @NotBlank
	@Column(nullable = false, unique = true)
    private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]"; // Written manually
	}
    
}
