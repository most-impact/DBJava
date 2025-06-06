package pro.sky.DBJava;

import pro.sky.DBJava.controller.FacultyController;
import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.model.Student;
import pro.sky.DBJava.service.FacultyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Blue");

        Mockito.when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science"));
    }

    @Test
    void getFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Blue");

        Mockito.when(facultyService.getFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science"));
    }

    @Test
    void updateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Green");

        Mockito.when(facultyService.updateFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    void deleteFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Blue");

        Mockito.when(facultyService.deleteFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void filterFacultiesByColor() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Blue");

        Mockito.when(facultyService.getFaculties()).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/filter").param("color", "Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Blue"));
    }

    @Test
    void getFacultiesByNameOrColor() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Blue");

        Mockito.when(facultyService.findFacultiesByNameOrColor("Science")).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/search").param("query", "Science"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Science"));
    }

    @Test
    void getStudentsOfFaculty() throws Exception {
        Student student = new Student(1L, "John", 20, null);
        Faculty faculty = new Faculty(1L, "Science", "Blue");
        faculty.setStudents(List.of(student));

        Mockito.when(facultyService.getFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }
}