package com.dsleandro.university.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 3, max = 40)
	@NotBlank
	@Column(name = "name")
	private String name;

	@Size(min = 3, max = 40)
	@NotBlank
	@Column(name = "schedule")
	private String schedule;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_professor")
	private Professor professor;

	@PositiveOrZero
	@Max(100)
	@Column(name = "max_quota")
	private int maxQuota;

	@NotBlank
	@Column(name = "description")
	private String description;

	@ManyToMany(mappedBy = "subjects")
	private List<User> users;

}
