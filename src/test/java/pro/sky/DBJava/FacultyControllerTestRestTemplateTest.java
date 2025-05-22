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
class FacultyControllerTestRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Faculty testFaculty;

    @BeforeEach
    void setUp() {
        testFaculty = new Faculty(null, "Test Faculty", "Blue");
    }

    @Test
    void testCreateFaculty() {
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testFaculty.getName(), response.getBody().getName());
    }

    @Test
    void testGetFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        Long id = createResponse.getBody().getId();

        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + id, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testFaculty.getName(), response.getBody().getName());
    }

    @Test
    void testUpdateFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        Faculty createdFaculty = createResponse.getBody();
        createdFaculty.setName("Updated Faculty");

        HttpEntity<Faculty> entity = new HttpEntity<>(createdFaculty);
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty", HttpMethod.PUT, entity, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Faculty", response.getBody().getName());
    }

    @Test
    void testDeleteFaculty() {
        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        Long id = createResponse.getBody().getId();

        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty/" + id, HttpMethod.DELETE, null, Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ResponseEntity<Faculty> getResponse = restTemplate.getForEntity("/faculty/" + id, Faculty.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNull(getResponse.getBody());
    }

    @Test
    void testFilterFacultiesByColor() {
        restTemplate.postForEntity("/faculty", testFaculty, Faculty.class);
        ResponseEntity<List> response = restTemplate.getForEntity("/faculty/filter?color=Blue", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

}