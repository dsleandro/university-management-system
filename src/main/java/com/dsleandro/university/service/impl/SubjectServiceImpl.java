package com.dsleandro.university.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsleandro.university.entity.Subject;
import com.dsleandro.university.repository.SubjectRepository;
import com.dsleandro.university.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository repository;

	@Override
	public List<Subject> getAllSubjects() {
		return repository.findAll();
	}

	@Override
	public void saveSubject(Subject subject) {
		this.repository.save(subject);

	}

	@Override
	public Subject getSubject(int id) {
		Optional<Subject> optional = repository.findById(id);
		Subject subject = null;

		if (optional.isPresent()) {
			subject = optional.get();
		} else {
			throw new RuntimeException("Subject not found");
		}

		return subject;
	}

	@Override
	public void deleteSubject(int id) {
		this.repository.deleteById(id);

	}

}
