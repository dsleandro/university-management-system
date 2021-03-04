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
import com.dsleandro.university.entity.Role;
import com.dsleandro.university.entity.User;
import com.dsleandro.university.service.SubjectService;
import com.dsleandro.university.service.RoleService;
import com.dsleandro.university.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SubjectService subjectService;

	@GetMapping(path = { "/", "/login" })
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

	@GetMapping("/index")
	public String index(@AuthenticationPrincipal UserDetails auth, Model model) {
		User user = userService.findUserByDni(auth.getUsername());
		Role adminRole = roleService.findRoleByName("ADMIN");
		Role studentRole = roleService.findRoleByName("STUDENT");
		Role role = user.getRole();
		model.addAttribute("userName", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("admin", role.getName().equals(adminRole.getName()) ? user : null);
		model.addAttribute("student", role.getName().equals(studentRole.getName()) ? user : null);
		return "user/index";
	}

	@GetMapping("/enrollSubject/{id}")
	public String enrollSubject(@AuthenticationPrincipal UserDetails auth, @PathVariable(value = "id") int id,
			Model model) {

		User user = userService.findUserByDni(auth.getUsername());
		Subject subject = subjectService.getSubject(id);

		Set<Subject> setSubjects = user.getSubjects();
		setSubjects.add(subject);
		user.setSubjects(setSubjects);
		userService.saveUser(user);

		List<Subject> listSubjects = subjectService.getAllSubjects();

		for (Subject c : setSubjects) {
			listSubjects.remove(c);
		}
		model.addAttribute("msg", "Inscripción exitosa a la materia: \"" + subject.getName() + "\"");
		model.addAttribute("listSubjects", listSubjects);

		return "student/list_subjects";

	}

	@GetMapping("/unenrollSubject/{id}")
	public String unenrollSubject(@AuthenticationPrincipal UserDetails auth, @PathVariable(value = "id") int id, Model model) {

		User user = userService.findUserByDni(auth.getUsername());
		Subject subject = subjectService.getSubject(id);

		Set<Subject> setSubjects = user.getSubjects();
		setSubjects.remove(subject);
		user.setSubjects(setSubjects);
		userService.saveUser(user);

		model.addAttribute("enrolledSubjectsList", user.getSubjects());
		model.addAttribute("msg", "Ya no estas inscripto a: \"" + subject.getName() + "\"");
		
		return "student/enrolled_subjects";
	}

	@GetMapping("/subjectsListToEnroll")
	public String subjectsListToEnroll(@AuthenticationPrincipal UserDetails auth, Model model) {
		User user = userService.findUserByDni(auth.getUsername());
		Set<Subject> setSubjects = user.getSubjects();
		List<Subject> listSubjects = subjectService.getAllSubjects();

		for (Subject subject : setSubjects) { // delete subjects where user already enrolled
			listSubjects.remove(subject);
		}

		model.addAttribute("listSubjects", listSubjects);
		return "student/list_subjects";
	}

	@GetMapping("/enrolledSubjectsList")
	public String showListEnrolledSubjects(@AuthenticationPrincipal UserDetails auth, Model model) {

		User user = userService.findUserByDni(auth.getUsername());
		model.addAttribute("enrolledSubjectsList", user.getSubjects());

		return "student/enrolled_subjects";
	}

	@GetMapping("/subjectDescription/{id}")
	public String showSubjectDescription(@PathVariable(value = "id") int id, Model model) {
		model.addAttribute("subject", subjectService.getSubject(id));
		return "student/subject_description";
	}

}
