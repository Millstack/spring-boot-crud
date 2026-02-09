package com.jsp.SpringBootCRUD.Controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.jsp.SpringBootCRUD.Dto.Student;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestAssuredTest {

    @LocalServerPort
    private int port;

    private Student student;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        student = new Student();
        student.setId(1);
        student.setName("John Doe");
        student.setEmail("john@example.com");
    }

    @Test
    void testSaveStudent() {
        given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(201))
                .body("message", equalTo("Student saved successfully"))
                .body("data.name", equalTo("John Doe"))
                .body("data.email", equalTo("john@example.com"));
    }

    @Test
    void testGetStudentById() {
        // First save a student
        int savedId = given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .extract().path("data.id");

        // Then get it
        given()
                .when()
                .get("/student/" + savedId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(200))
                .body("message", equalTo("Student retrieved successfully"))
                .body("data.id", equalTo(savedId));
    }

    @Test
    void testGetAllStudents() {
        // First save a student
        given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200);

        // Then get all
        given()
                .when()
                .get("/student")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(200))
                .body("message", equalTo("Students retrieved successfully"))
                .body("data", hasSize(1))
                .body("data[0].name", equalTo("John Doe"));
    }

    @Test
    void testUpdateStudent() {
        // First save a student
        int savedId = given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .extract().path("data.id");

        // Then update
        student.setName("Test Name");
        given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .put("/student/" + savedId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(200))
                .body("message", equalTo("Student updated successfully"))
                .body("data.name", equalTo("Test Name"))
                .body("data.email", equalTo("john@example.com"));
    }

    @Test
    void testUpdateStudentEmail() {
        // First save a student
        int savedId = given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .extract().path("data.id");

        // Then update email
        student.setEmail("updated@example.com");
        given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .put("/student/" + savedId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(200))
                .body("message", equalTo("Student updated successfully"))
                .body("data.email", equalTo("updated@example.com"));
    }

    @Test
    void testDeleteStudent() {
        // First save a student
        int savedId = given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .extract().path("data.id");

        // Then delete
        given()
                .when()
                .delete("/student/" + savedId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(200))
                .body("message", equalTo("Student deleted successfully"))
                .body("data", equalTo("Student deleted"));
    }

    // Negative Test Cases

    @Test
    void testGetStudentByIdNotFound() {
        given()
                .when()
                .get("/student/999")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(404))
                .body("message", equalTo("Student not found"));
    }

    @Test
    void testSaveStudentInvalidJson() {
        given()
                .contentType(ContentType.JSON)
                .body("{invalid json}")
                .when()
                .post("/student")
                .then()
                .statusCode(400);
    }

    @Test
    void testSaveStudentEmptyRequest() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(201))
                .body("message", equalTo("Student saved successfully"));
    }

    @Test
    void testUpdateStudentNotFound() {
        given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .put("/student/999")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(404))
                .body("message", equalTo("Student not found for update"));
    }

    @Test
    void testDeleteStudentNotFound() {
        given()
                .when()
                .delete("/student/999")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(404))
                .body("message", equalTo("Student not found for deletion"));
    }

    @Test
    void testGetStudentByIdInvalidPathVariable() {
        // Spring automatically returns 400 for non-integer path variables
        given()
                .when()
                .get("/student/abc")
                .then()
                .statusCode(400);
    }

    @Test
    void testGetStudentByIdNotFoundAfterDelete() {
        // Save a student, delete it, then try to get it - should return 404
        int savedId = given()
                .contentType(ContentType.JSON)
                .body(student)
                .when()
                .post("/student")
                .then()
                .statusCode(200)
                .extract().path("data.id");

        // Delete the student
        given()
                .when()
                .delete("/student/" + savedId)
                .then()
                .statusCode(200)
                .body("statusCode", equalTo(200));

        // Try to get the deleted student - should return 404
        given()
                .when()
                .get("/student/" + savedId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("statusCode", equalTo(404))
                .body("message", equalTo("Student not found"));
    }
}
