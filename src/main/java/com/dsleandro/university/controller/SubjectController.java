package com.dsleandro.university.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsleandro.university.service.ProfessorService;
import com.dsleandro.university.entity.Subject;
import com.dsleandro.university.entity.Professor;
import com.dsleandro.university.service.SubjectService;

@RequestMapping("/admin")
@Controller
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ProfessorService professorService;

	@PostMapping("/saveSubject")
	public String saveSubject(@Valid @ModelAttribute("subject") Subject subject,
	BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("subject", subject);
			model.addAttribute("listProfessors", professorService.getAllProfessors());
			return "admin/new_subject";
		}
		
		subjectService.saveSubject(subject);
		model.addAttribute("msgRegister", "La Materia \"" + subject.getName() + "\" ha sido registrada!");
		model.addAttribute("subjectsList", subjectService.getAllSubjects());
		return "admin/subjects_list";
	}

	@GetMapping("/subjectsList")
	public String subjectsList(Model model) {
		model.addAttribute("subjectsList", subjectService.getAllSubjects());
		return "admin/subjects_list";
	}

	@GetMapping("/addSubjectForm")
	public String addSubjectForm(Model model) {
		Subject subject = new Subject();
		List<Professor> list = professorService.getAllProfessors();

		if (list.isEmpty()) {
			model.addAttribute("msgError", "Primero agrega un profesor!");
			return "admin/subjects_list";
		} else {

			model.addAttribute("subject", subject);
			model.addAttribute("listProfessors", professorService.getAllProfessors());
			return "admin/new_subject";
		}

	}

	@GetMapping("/subjectUpdate/{id}")
	public String SubjectUpdateForm(@PathVariable(value = "id") int id, Model model) {
		Subject subject = subjectService.getSubject(id);

		model.addAttribute("subject", subject);
		model.addAttribute("listProfessors", professorService.getAllProfessors());

		return "admin/new_subject";
	}

	@GetMapping("/subjectDelete/{id}")
	public String deleteSubject(@PathVariable(value = "id") int id, Model model) {
		Subject subject = subjectService.getSubject(id);
		subjectService.deleteSubject(id);

		model.addAttribute("msgDeleted", "La materia \"" + subject.getName() + "\" ha sido eliminada!");
		model.addAttribute("subjectsList", subjectService.getAllSubjects());

		return "admin/subjects_list";
	}
}
