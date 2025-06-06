package pro.sky.DBJava;

import pro.sky.DBJava.controller.StudentController;
import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.model.Student;
import pro.sky.DBJava.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createStudent() throws Exception {
        Student student = new Student(1L, "John", 20, null);

        Mockito.when(studentService.studentCreate(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void getStudent() throws Exception {
        Student student = new Student(1L, "John", 20, null);
        Mockito.when(studentService.getStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void updateStudent() throws Exception {
        Student student = new Student(1L, "John", 21, null);

        Mockito.when(studentService.updateStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(put("/student")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(21));
    }

    @Test
    void deleteStudent() throws Exception {
        Student student = new Student(1L, "John", 20, null);
        Mockito.when(studentService.deleteStudent(1L)).thenReturn(student);

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void filterStudentsByAge() throws Exception {
        Student student = new Student(1L, "John", 20, null);

        Mockito.when(studentService.getStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/student/filter").param("age", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    void getStudentsByAgeBetween() throws Exception {
        Student student = new Student(1L, "John", 20, null);

        Mockito.when(studentService.findByAgeBetween(18, 22)).thenReturn(List.of(student));

        mockMvc.perform(get("/student/by-age").param("min", "18").param("max", "22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    void getFacultyOfStudent() throws Exception {
        Faculty faculty = new Faculty(1L, "Science", "Blue");
        Student student = new Student(1L, "John", 20, faculty);

        Mockito.when(studentService.getStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Science"));
    }
}
