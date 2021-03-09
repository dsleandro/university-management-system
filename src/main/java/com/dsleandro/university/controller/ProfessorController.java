package com.dsleandro.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import com.dsleandro.university.entity.Professor;
import com.dsleandro.university.service.ProfessorService;

@RequestMapping("/admin")
@Controller
public class ProfessorController {

	@Autowired
	private ProfessorService profService;

	@PostMapping("/saveProfessor")
	public String saveProfessor(@Valid @ModelAttribute("professor") Professor professor, Model model) {
		profService.saveProfessor(professor);

		model.addAttribute("msgRegister", "El profesor \"" + professor.getFirstName() + " " + professor.getLastName() + "\" ha sido Registrado");

		model.addAttribute("listProfessors", profService.getAllProfessors());
		return "admin/list_professors";
	}

	@GetMapping("/professors")
	public String ProfessorsList(Model model) {
		model.addAttribute("listProfessors", profService.getAllProfessors());
		return "admin/list_professors";
	}

	@GetMapping("/addProfessor")
	public String NewProfessorForm(Model model) {
		Professor professor = new Professor();
		model.addAttribute("professor", professor);
		return "admin/new_professor";
	}

	@GetMapping("/professorUpdate/{id}")
	public String updateProfessorForm(@PathVariable(value = "id") int id, Model model) {
		Professor professor = profService.getProfessor(id);

		model.addAttribute("professor", professor);

		return "admin/new_professor";
	}

	@GetMapping("/professorDelete/{id}")
	public String deleteProfessor(@PathVariable(value = "id") int id, Model model) {
		Professor professor = profService.getProfessor(id);
		profService.deleteProfessor(id);

		model.addAttribute("msgDeleted", "El profesor \"" + professor.getFirstName() + " " + professor.getLastName()
				+ "\" ha sido eliminado!");
		model.addAttribute("listProfessors", profService.getAllProfessors());
		return "admin/list_professors";
	}
}
