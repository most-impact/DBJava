package pro.sky.DBJava.service;

import pro.sky.DBJava.model.Student;
import pro.sky.DBJava.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student studentCreate(Student student){
        return studentRepository.save(student);
    }

    public Student getStudent(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student){
        if (studentRepository.existsById(student.getId())){
            return studentRepository.save(student);
        }
        return null;
    }

    public Student deleteStudent(Long id){
        if (studentRepository.existsById(id)){
            Student student = studentRepository.findById(id).orElse(null);
            studentRepository.deleteById(id);
            return student;
        }
        return null;
    }

    public List<Student> findByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

}