package lms.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lms.beans.Student;
import lms.services.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	StudentService studentService;
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public Student addStudent(@RequestBody Student student) {
		System.out.println("Add Student");
		studentService.addStudent(student);
		return student;
	}
	
	@RequestMapping(value="/getAllStudents", method = RequestMethod.GET)
	public List getAllStudents() {
		List students = studentService.getAllStudents();
		return students;
	}
}
