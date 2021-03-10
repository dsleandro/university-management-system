package com.dsleandro.university.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dsleandro.university.entity.Subject;
import com.dsleandro.university.entity.User;
import com.dsleandro.university.service.SubjectService;
import com.dsleandro.university.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SubjectService subjectService;

	@GetMapping(path = "/login")
	public String loginForm(Model model) {

		model.addAttribute("user", new User());
		return "user/login";
	}

	@GetMapping("/signup")
	public String signupForm(Model model) {

		model.addAttribute("user", new User());
		return "user/signup";
	}

	@PostMapping("/signup")
	public String signup(@Valid User user, BindingResult bindingResult, Model model) {
		User userExists = userService.findUserByDni(user.getDni());

		if (userExists != null) {
			bindingResult.rejectValue("dni", "error.user", "El DNI ya existe!");
			model.addAttribute("msg", "El DNI ya existe!");
			return "user/signup";
		}

		if (bindingResult.hasErrors()) {
			return "user/signup";
		}

		userService.saveUser(user);
		model.addAttribute("msg", "Usuario registrado exitosamente!");
		model.addAttribute("user", new User());

		return "user/login";
	}

	@GetMapping("/")
	public String index(@AuthenticationPrincipal UserDetails auth, Model model) {
		User user = userService.findUserByDni(auth.getUsername());

		model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
		return "user/index";
	}

	@GetMapping("/access_denied")
	public String accessDenied() {
		return "error/access_denied";
	}

	@GetMapping("/student/enrollSubject/{id}")
	public String enrollSubject(@AuthenticationPrincipal UserDetails auth, @PathVariable(value = "id") int id,
			Model model) {

		User user = userService.findUserByDni(auth.getUsername());
		Subject subject = subjectService.getSubject(id);

		Set<Subject> setSubjects = user.getSubjects();
		setSubjects.add(subject);
		user.setSubjects(setSubjects);
		userService.saveUser(user);

		subject.setQuotas(subject.getQuotas() - 1);
		subjectService.saveSubject(subject);

		List<Subject> listSubjects = subjectService.getAllOrderByNameAsc();

		for (Subject s : setSubjects) {
			listSubjects.remove(s);
		}
		model.addAttribute("msg", "Inscripción exitosa a la materia: \"" + subject.getName() + "\"");
		model.addAttribute("listSubjects", listSubjects);

		return "student/list_subjects";

	}

	@GetMapping("/student/unenrollSubject/{id}")
	public String unenrollSubject(@AuthenticationPrincipal UserDetails auth, @PathVariable(value = "id") int id,
			Model model) {

		User user = userService.findUserByDni(auth.getUsername());
		Subject subject = subjectService.getSubject(id);

		Set<Subject> setSubjects = user.getSubjects();
		setSubjects.remove(subject);
		user.setSubjects(setSubjects);
		userService.saveUser(user);

		subject.setQuotas(subject.getQuotas() + 1);
		subjectService.saveSubject(subject);

		model.addAttribute("enrolledSubjectsList", user.getSubjects());
		model.addAttribute("msg", "Ya no estas inscripto a: \"" + subject.getName() + "\"");

		return "student/enrolled_subjects";
	}

	@GetMapping("/student/subjectsListToEnroll")
	public String subjectsListToEnroll(@AuthenticationPrincipal UserDetails auth, Model model) {
		User user = userService.findUserByDni(auth.getUsername());
		Set<Subject> setSubjects = user.getSubjects();
		List<Subject> listSubjects = subjectService.getAllOrderByNameAsc();

		for (Subject subject : setSubjects) { // delete subjects where user already enrolled
			listSubjects.remove(subject);
		}

		model.addAttribute("listSubjects", listSubjects);
		return "student/list_subjects";
	}

	@GetMapping("/student/enrolledSubjectsList")
	public String showListEnrolledSubjects(@AuthenticationPrincipal UserDetails auth, Model model) {

		User user = userService.findUserByDni(auth.getUsername());
		model.addAttribute("enrolledSubjectsList", user.getSubjects());

		return "student/enrolled_subjects";
	}

	@GetMapping("/student/subjectDescription/{id}")
	public String showSubjectDescription(@PathVariable(value = "id") int id, Model model) {
		model.addAttribute("subject", subjectService.getSubject(id));
		return "student/subject_description";
	}

}
