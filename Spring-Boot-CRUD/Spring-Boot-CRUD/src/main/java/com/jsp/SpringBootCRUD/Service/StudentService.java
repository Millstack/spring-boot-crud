package com.jsp.SpringBootCRUD.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.SpringBootCRUD.Dao.StudentDao;
import com.jsp.SpringBootCRUD.Dto.ResponseStructure;
import com.jsp.SpringBootCRUD.Dto.Student;

@Service
public class StudentService {
	
	@Autowired
	StudentDao studentDao;
	
//	save student
	public ResponseStructure<Student> saveStudent(Student student){
		ResponseStructure<Student> responseStructure = new ResponseStructure<Student>();
		Student student1 = studentDao.saveStudent(student);
		if(student1 != null) {
			responseStructure.setData(student1);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Student saved successfully");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Student has failed to save");
		}
		return responseStructure;
	}
	
//	get student by id
	public ResponseStructure<Student> getStudentById(Integer id){
		ResponseStructure<Student> responseStructure = new ResponseStructure<Student>();
		Student student = studentDao.getStudentById(id);
		if(student != null) {
			responseStructure.setData(student);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Student got by id");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Student don't exists");
		}
		return responseStructure;
	}
	
//	get all students
	public ResponseStructure<List<Student>> getAllStudent(){
		ResponseStructure<List<Student>> responseStructure = new ResponseStructure<List<Student>>();
		List<Student> student = studentDao.getAllStudent();
		if(student.size() > 0) {
			responseStructure.setData(student);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Here are the list of all Students");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("No student record exists in database");
		}
		return responseStructure;
	}
	
//	update student
	public ResponseStructure<Student> updateStudent(Student student, Integer id){
		ResponseStructure<Student> responseStructure = new ResponseStructure<Student>();
		Student student1 = studentDao.updateStudent(student, id);
		if(student1 != null) {
			responseStructure.setData(student1);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Student updated successfully");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Student don't exists");
		}
		return responseStructure;
	}
	
//	delete student
	public ResponseStructure<String> deleteStudent(Integer id){
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		boolean isTrue = studentDao.deleteStudent(id);
		if(isTrue) {
			responseStructure.setData("Student selected");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Student deleted successfully");
		} else {
			responseStructure.setData("Student not selected");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Student has failed to get delete");
		}
		return responseStructure;
	}
}