package com.jsp.SpringBootCRUD.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.SpringBootCRUD.Dto.ResponseStructure;
import com.jsp.SpringBootCRUD.Dto.Student;
import com.jsp.SpringBootCRUD.Service.StudentService;

@WebMvcTest(StudentController.class)
public class StudentControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private ResponseStructure<Student> successResponse;
    private ResponseStructure<String> deleteResponse;

    /**
     * Setup method that runs before each test.
     * Initializes test data including a sample student object and response structures
     * that will be used across multiple test methods for mocking service responses.
     */
    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1);
        student.setName("John Doe");
        student.setEmail("john@example.com");

        successResponse = new ResponseStructure<>();
        successResponse.setStatusCode(200);
        successResponse.setMessage("Student retrieved successfully");
        successResponse.setData(student);

        deleteResponse = new ResponseStructure<>();
        deleteResponse.setStatusCode(200);
        deleteResponse.setMessage("Student deleted successfully");
        deleteResponse.setData("Student deleted");
    }

    /**
     * Test case for saving a new student via POST request.
     * Verifies that the student is saved successfully and returns appropriate response.
     */
    @Test
    void testSaveStudent() throws Exception {
        ResponseStructure<Student> saveResponse = new ResponseStructure<>();
        saveResponse.setStatusCode(201);
        saveResponse.setMessage("Student saved successfully");
        saveResponse.setData(student);

        when(studentService.saveStudent(any(Student.class))).thenReturn(saveResponse);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Student saved successfully"))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    /**
     * Test case for retrieving a student by ID via GET request.
     * Verifies that the correct student is retrieved and returned in the response.
     */
    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudentById(1)).thenReturn(successResponse);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John Doe"));
    }

    /**
     * Test case for retrieving all students via GET request.
     * Verifies that all students are retrieved and returned in a list format.
     */
    @Test
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(student);
        ResponseStructure<List<Student>> listResponse = new ResponseStructure<>();
        listResponse.setStatusCode(200);
        listResponse.setMessage("Students retrieved successfully");
        listResponse.setData(students);

        when(studentService.getAllStudent()).thenReturn(listResponse);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Students retrieved successfully"))
                .andExpect(jsonPath("$.data[0].name").value("John Doe"));
    }

    /**
     * Test case for updating an existing student via PUT request.
     * Verifies that the student is updated successfully and returns appropriate response.
     */
    @Test
    void testUpdateStudent() throws Exception {
        ResponseStructure<Student> updateResponse = new ResponseStructure<>();
        updateResponse.setStatusCode(200);
        updateResponse.setMessage("Student updated successfully");
        updateResponse.setData(student);

        when(studentService.updateStudent(any(Student.class), eq(1))).thenReturn(updateResponse);

        mockMvc.perform(put("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student updated successfully"))
                .andExpect(jsonPath("$.data.name").value("John Doe"));
    }

    /**
     * Test case for deleting a student by ID via DELETE request.
     * Verifies that the student is deleted successfully and returns confirmation message.
     */
    @Test
    void testDeleteStudent() throws Exception {
        when(studentService.deleteStudent(1)).thenReturn(deleteResponse);

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student deleted successfully"))
                .andExpect(jsonPath("$.data").value("Student deleted"));
    }

    /**
     * Test case for retrieving a non-existent student by ID.
     * Verifies that appropriate 404 error response is returned when student is not found.
     */
    @Test
    void testGetStudentByIdNotFound() throws Exception {
        ResponseStructure<Student> notFoundResponse = new ResponseStructure<>();
        notFoundResponse.setStatusCode(404);
        notFoundResponse.setMessage("Student not found");

        when(studentService.getStudentById(999)).thenReturn(notFoundResponse);

        mockMvc.perform(get("/student/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Student not found"));
    }

    // Negative Test Cases

    /**
     * Test case for updating a non-existent student.
     * Verifies that appropriate 404 error response is returned when student to update is not found.
     */
    @Test
    void testUpdateStudentNotFound() throws Exception {
        ResponseStructure<Student> notFoundResponse = new ResponseStructure<>();
        notFoundResponse.setStatusCode(404);
        notFoundResponse.setMessage("Student not found for update");

        when(studentService.updateStudent(any(Student.class), eq(999))).thenReturn(notFoundResponse);

        mockMvc.perform(put("/student/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Student not found for update"));
    }

    /**
     * Test case for deleting a non-existent student.
     * Verifies that appropriate 404 error response is returned when student to delete is not found.
     */
    @Test
    void testDeleteStudentNotFound() throws Exception {
        ResponseStructure<String> notFoundResponse = new ResponseStructure<>();
        notFoundResponse.setStatusCode(404);
        notFoundResponse.setMessage("Student not found for deletion");

        when(studentService.deleteStudent(999)).thenReturn(notFoundResponse);

        mockMvc.perform(delete("/student/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Student not found for deletion"));
    }

    /**
     * Test case for saving a student with invalid email format.
     * Verifies that appropriate validation error response is returned for invalid email.
     */
    @Test
    void testSaveStudentWithInvalidEmail() throws Exception {
        Student invalidStudent = new Student();
        invalidStudent.setId(1);
        invalidStudent.setName("John Doe");
        invalidStudent.setEmail("invalid-email"); // Invalid email format

        ResponseStructure<Student> saveResponse = new ResponseStructure<>();
        saveResponse.setStatusCode(400);
        saveResponse.setMessage("Invalid email format");
        saveResponse.setData(invalidStudent);

        when(studentService.saveStudent(any(Student.class))).thenReturn(saveResponse);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Invalid email format"));
    }

    /**
     * Test case for saving a student with empty name field.
     * Verifies that appropriate validation error response is returned for empty name.
     */
    @Test
    void testSaveStudentWithEmptyName() throws Exception {
        Student emptyNameStudent = new Student();
        emptyNameStudent.setId(1);
        emptyNameStudent.setName(""); // Empty name
        emptyNameStudent.setEmail("john@example.com");

        ResponseStructure<Student> saveResponse = new ResponseStructure<>();
        saveResponse.setStatusCode(400);
        saveResponse.setMessage("Name is required");
        saveResponse.setData(emptyNameStudent);

        when(studentService.saveStudent(any(Student.class))).thenReturn(saveResponse);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyNameStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Name is required"));
    }

    /**
     * Test case for saving a student with invalid JSON format.
     * Verifies that appropriate bad request response is returned for malformed JSON.
     */
    @Test
    void testSaveStudentWithInvalidJson() throws Exception {
        String invalidJson = "{invalid json}";

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test case for saving a student with empty request body.
     * Verifies that appropriate validation error response is returned for empty request body.
     */
    @Test
    void testSaveStudentWithEmptyBody() throws Exception {
        String emptyBody = "{}";

        ResponseStructure<Student> saveResponse = new ResponseStructure<>();
        saveResponse.setStatusCode(400);
        saveResponse.setMessage("Invalid request body");
        saveResponse.setData(null);

        when(studentService.saveStudent(any(Student.class))).thenReturn(saveResponse);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emptyBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Invalid request body"));
    }

    /**
     * Test case for retrieving a student with invalid path variable format.
     * Verifies that appropriate bad request response is returned for invalid ID format.
     */
    @Test
    void testGetStudentByIdWithInvalidPathVariable() throws Exception {
        mockMvc.perform(get("/student/abc"))
                .andExpect(status().isBadRequest());
    }
}
