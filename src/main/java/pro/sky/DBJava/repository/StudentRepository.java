package pro.sky.DBJava.repository;

import pro.sky.DBJava.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int minAge, int maxAge);
    @Query("SELECT COUNT(s) FROM Student s")
    Long getTotalStudentsCount();
    @Query("SELECT AVG(s.age) FROM Student s")
    Double getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFiveStudents();

    @Query("SELECT s.name, s.age, f.name FROM Student s LEFT JOIN s.faculty f")
    List<Object[]> findStudentsWithFacultyNames();

    @Query(value = "SELECT s.name, s.age FROM student s INNER JOIN avatar a ON s.id = a.student_id", nativeQuery = true)
    List<Object[]> findStudentsWithAvatars();
}