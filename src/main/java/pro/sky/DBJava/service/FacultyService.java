package pro.sky.DBJava.service;

import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService{
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id){
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty updateFaculty(Faculty faculty){
        if (facultyRepository.existsById(faculty.getId())){
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public Faculty deleteFaculty(Long id){
        if (facultyRepository.existsById(id)){
            Faculty faculty = facultyRepository.findById(id).orElse(null);
            facultyRepository.deleteById(id);
            return faculty;
        }
        return null;
    }

    public List<Faculty> findFacultiesByNameOrColor(String query) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }
}