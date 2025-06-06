package pro.sky.DBJava.controller;

import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.model.Student;
import pro.sky.DBJava.service.FacultyService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private static final Logger logger = Logger.getLogger(StudentController.class.getName());

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Существующие методы остаются без изменений
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

    // Шаг 1: Эндпоинт для параллельного вывода имен
    @GetMapping("/print-parallel")
    public String printNamesParallel() {
        List<Student> students = studentService.getStudents();
        if (students.size() < 6) {
            return "Not enough students (minimum 6 required)";
        }

        // Основной поток: вывод первых двух имен
        System.out.println("Main thread - Student 1: " + students.get(0).getName());
        System.out.println("Main thread - Student 2: " + students.get(1).getName());

        // Первый параллельный поток: имена третьего и четвертого студента
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 - Student 3: " + students.get(2).getName());
            System.out.println("Thread 1 - Student 4: " + students.get(3).getName());
        });

        // Второй параллельный поток: имена пятого и шестого студента
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 - Student 5: " + students.get(4).getName());
            System.out.println("Thread 2 - Student 6: " + students.get(5).getName());
        });

        // Запуск потоков
        thread1.start();
        thread2.start();

        // Ожидание завершения потоков
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            logger.severe("Thread interrupted: " + e.getMessage());
        }

        return "Names printed in parallel. Check console for output.";
    }

    private synchronized void printName(String name, String threadInfo) {
        System.out.println(threadInfo + ": " + name);
    }

    @GetMapping("/print-synchronized")
    public String printNamesSynchronized() {
        List<Student> students = studentService.getStudents();
        if (students.size() < 6) {
            return "Not enough students (minimum 6 required)";
        }

        printName(students.get(0).getName(), "Main thread - Student 1");
        printName(students.get(1).getName(), "Main thread - Student 2");

        Thread thread1 = new Thread(() -> {
            printName(students.get(2).getName(), "Thread 1 - Student 3");
            printName(students.get(3).getName(), "Thread 1 - Student 4");
        });

        Thread thread2 = new Thread(() -> {
            printName(students.get(4).getName(), "Thread 2 - Student 5");
            printName(students.get(5).getName(), "Thread 2 - Student 6");
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            logger.severe("Thread interrupted: " + e.getMessage());
        }

        return "Names printed synchronized. Check console for output.";
    }
}