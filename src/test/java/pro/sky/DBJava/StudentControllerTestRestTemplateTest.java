package pro.sky.DBJava;

import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Student testStudent;
    private Faculty testFaculty;

    @BeforeEach
    void setUp() {
        testFaculty = new Faculty(null, "Test Faculty", "Blue");
        testStudent = new Student(null, "Test Student", 20, testFaculty);
    }

    @Test
    void testCreateStudent() {
        ResponseEntity<Student> response = restTemplate.postForEntity("/student", testStudent, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testStudent.getName(), response.getBody().getName());
    }

    @Test
    void testGetStudent() {
        // Сначала создаем студента
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student", testStudent, Student.class);
        Long id = createResponse.getBody().getId();

        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + id, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testStudent.getName(), response.getBody().getName());
    }

    @Test
    void testUpdateStudent() {
        // Создаем студента
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student", testStudent, Student.class);
        Student createdStudent = createResponse.getBody();
        createdStudent.setName("Updated Student");

        HttpEntity<Student> entity = new HttpEntity<>(createdStudent);
        ResponseEntity<Student> response = restTemplate.exchange("/student", HttpMethod.PUT, entity, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Student", response.getBody().getName());
    }

    @Test
    void testDeleteStudent() {
        // Создаем студента
        ResponseEntity<Student> createResponse = restTemplate.postForEntity("/student", testStudent, Student.class);
        Long id = createResponse.getBody().getId();

        ResponseEntity<Student> response = restTemplate.exchange("/student/" + id, HttpMethod.DELETE, null, Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // Проверяем, что студент удален
        ResponseEntity<Student> getResponse = restTemplate.getForEntity("/student/" + id, Student.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNull(getResponse.getBody());
    }

    @Test
    void testFilterStudentsByAge() {
        restTemplate.postForEntity("/student", testStudent, Student.class);
        ResponseEntity<List> response = restTemplate.getForEntity("/student/filter?age=20", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetStudentsByAgeBetween() {
        restTemplate.postForEntity("/student", testStudent, Student.class);
        ResponseEntity<List> response = restTemplate.getForEntity("/student/by-age?min=18&max=22", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }
}