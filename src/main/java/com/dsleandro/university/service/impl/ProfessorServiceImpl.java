package com.dsleandro.university.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsleandro.university.entity.Professor;
import com.dsleandro.university.repository.ProfessorRepository;
import com.dsleandro.university.service.ProfessorService;

@Service
public class ProfessorServiceImpl implements ProfessorService {

	@Autowired
	private ProfessorRepository repository;

	@Override
	public List<Professor> getAllProfessors() {
		return repository.findAll();
	}

	@Override
	public void saveProfessor(Professor professor) {
		this.repository.save(professor);
	}

	@Override
	public Professor getProfessor(int id) {
		Optional<Professor> optional = repository.findById(id);
		Professor professor = null;

		if (optional.isPresent()) {
			professor = optional.get();
		} else {
			throw new RuntimeException("Professor not found id");
		}

		return professor;
	}

	@Override
	public void deleteProfessor(int id) {
		this.repository.deleteById(id);
	}

}
