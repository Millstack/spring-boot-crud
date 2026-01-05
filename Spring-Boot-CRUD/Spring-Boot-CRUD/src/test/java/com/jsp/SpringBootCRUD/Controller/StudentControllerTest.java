package com.jsp.SpringBootCRUD.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private ResponseStructure<Student> studentResponse;
    private ResponseStructure<List<Student>> studentListResponse;
    private ResponseStructure<String> stringResponse;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1);
        student.setName("John Doe");
        student.setEmail("john@example.com");

        studentResponse = new ResponseStructure<>();
        studentResponse.setStatusCode(200);
        studentResponse.setMessage("Student saved successfully");
        studentResponse.setData(student);

        Student student2 = new Student();
        student2.setId(2);
        student2.setName("Jane Doe");
        student2.setEmail("jane@example.com");

        List<Student> students = Arrays.asList(student, student2);
        studentListResponse = new ResponseStructure<>();
        studentListResponse.setStatusCode(200);
        studentListResponse.setMessage("Students retrieved successfully");
        studentListResponse.setData(students);

        stringResponse = new ResponseStructure<>();
        stringResponse.setStatusCode(200);
        stringResponse.setMessage("Student deleted successfully");
        stringResponse.setData("Student deleted");
    }

    @Test
    void testSaveStudent() throws Exception {
        when(studentService.saveStudent(any(Student.class))).thenReturn(studentResponse);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student saved successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudentById(anyInt())).thenReturn(studentResponse);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student saved successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudent()).thenReturn(studentListResponse);

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Students retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[1].id").value(2));
    }

    @Test
    void testUpdateStudent() throws Exception {
        when(studentService.updateStudent(any(Student.class), anyInt())).thenReturn(studentResponse);

        mockMvc.perform(put("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student saved successfully"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        when(studentService.deleteStudent(anyInt())).thenReturn(stringResponse);

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Student deleted successfully"))
                .andExpect(jsonPath("$.data").value("Student deleted"));
    }

    // Negative Test Cases

    @Test
    void testGetStudentByIdNotFound() throws Exception {
        ResponseStructure<Student> notFoundResponse = new ResponseStructure<>();
        notFoundResponse.setStatusCode(404);
        notFoundResponse.setMessage("Student not found");
        notFoundResponse.setData(null);

        when(studentService.getStudentById(anyInt())).thenReturn(notFoundResponse);

        mockMvc.perform(get("/student/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Student not found"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testSaveStudentInvalidJson() throws Exception {
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{invalid json}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveStudentEmptyRequest() throws Exception {
        ResponseStructure<Student> badRequestResponse = new ResponseStructure<>();
        badRequestResponse.setStatusCode(400);
        badRequestResponse.setMessage("Invalid student data");
        badRequestResponse.setData(null);

        when(studentService.saveStudent(any(Student.class))).thenReturn(badRequestResponse);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("Invalid student data"));
    }

    @Test
    void testUpdateStudentNotFound() throws Exception {
        ResponseStructure<Student> notFoundResponse = new ResponseStructure<>();
        notFoundResponse.setStatusCode(404);
        notFoundResponse.setMessage("Student not found for update");
        notFoundResponse.setData(null);

        when(studentService.updateStudent(any(Student.class), anyInt())).thenReturn(notFoundResponse);

        mockMvc.perform(put("/student/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Student not found for update"));
    }

    @Test
    void testDeleteStudentNotFound() throws Exception {
        ResponseStructure<String> notFoundResponse = new ResponseStructure<>();
        notFoundResponse.setStatusCode(404);
        notFoundResponse.setMessage("Student not found for deletion");
        notFoundResponse.setData(null);

        when(studentService.deleteStudent(anyInt())).thenReturn(notFoundResponse);

        mockMvc.perform(delete("/student/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").value("Student not found for deletion"));
    }

    @Test
    void testGetStudentByIdInvalidPathVariable() throws Exception {
        // Spring automatically returns 400 for non-integer path variables
        mockMvc.perform(get("/student/abc"))
                .andExpect(status().isBadRequest());
    }
}
