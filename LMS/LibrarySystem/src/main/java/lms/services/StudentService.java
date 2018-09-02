package lms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import lms.beans.Student;

@Service("StudentService")
public class StudentService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	final String COLLECTION = "student";
	
	public void addStudent(Student student) {
		mongoTemplate.insert(student,COLLECTION);
	}
	
	public void updateStudent(Student student) {
		mongoTemplate.save(student,COLLECTION);
	}
	
	public void deleteStudent(Student student) {
		mongoTemplate.remove(student,COLLECTION);
	}
	
	public void deleteAllStudents() {
		mongoTemplate.remove(new Query(),COLLECTION);
	}
	
	public Student findStudent(Student student) {
		Query query = new Query(Criteria.where("_id").is(student.getRollno()));
		return (Student) mongoTemplate.findOne(query, Student.class);
	}
	
	public List<Student> getAllStudents(){
		return (List<Student>) mongoTemplate.findAll(Student.class);
	}
}
