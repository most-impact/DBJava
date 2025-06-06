package pro.sky.DBJava.controller;

import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.model.Student;
import pro.sky.DBJava.service.FacultyService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.studentCreate(student);
    }
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }
    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }
    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
    @GetMapping("/filter")
    public List<Student> filterStudentsByAge(@RequestParam int age) {
        return studentService.getStudents()
                .stream()
                .filter(student -> student.getAge() == age)
                .toList();
    }
    @GetMapping("/by-age")
    public List<Student> getStudentsByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }
    @GetMapping("/{id}/faculty")
    public Faculty getFacultyOfStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        return student != null ? student.getFaculty() : null;
    }
    @GetMapping("/count")
    public Long getTotalStudentsCount() {
        return studentService.getTotalStudentsCount();
    }
    @GetMapping("/average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }
    @GetMapping("/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.findLastFiveStudents();
    }

    @GetMapping("/names-starting-with-a")
    public List<String> getStudentNamesStartingWithA() {
        return studentService.getStudents()
                .stream()
                .map(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    @GetMapping("/average-age-stream")
    public Double getAverageAgeStream() {
        return studentService.getStudents()
                .stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0);
    }
}