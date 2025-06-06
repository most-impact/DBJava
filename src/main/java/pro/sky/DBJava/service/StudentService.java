package pro.sky.DBJava.service;

import pro.sky.DBJava.model.Student;
import pro.sky.DBJava.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    Logger logger = Logger.getLogger(StudentService.class.getName());

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student studentCreate(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudents() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        if (studentRepository.existsById(student.getId())) {
            return studentRepository.save(student);
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).orElse(null);
            studentRepository.deleteById(id);
            return student;
        }
        return null;
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method for find students by age between");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Long getTotalStudentsCount() {
        logger.info("Was invoked method for get total students count");
        return studentRepository.getTotalStudentsCount();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method for get average age");
        return studentRepository.getAverageAge();
    }

    public List<Student> findLastFiveStudents() {
        logger.info("Was invoked method for find last five students");
        return studentRepository.findLastFiveStudents();
    }
}