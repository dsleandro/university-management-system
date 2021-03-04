package com.dsleandro.university.service;

import java.util.List;

import com.dsleandro.university.entity.Subject;

public interface SubjectService {

	List<Subject> getAllSubjects();

	void saveSubject(Subject subject);

	Subject getSubject(int id);

	public void deleteSubject(int id);
}
