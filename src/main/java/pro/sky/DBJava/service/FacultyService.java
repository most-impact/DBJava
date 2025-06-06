package pro.sky.DBJava.service;

import pro.sky.DBJava.model.Faculty;
import pro.sky.DBJava.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class FacultyService{
    private final FacultyRepository facultyRepository;

    Logger logger = Logger.getLogger(FacultyService.class.getName());

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        logger.info("Creating faculty: " + faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id){
        logger.info("Was invoked method for get faculty");
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    public Faculty updateFaculty(Faculty faculty){
        logger.info("Was invoked method for update faculty");
        if (facultyRepository.existsById(faculty.getId())){
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public Faculty deleteFaculty(Long id){
        logger.info("Was invoked method for delete faculty");
        if (facultyRepository.existsById(id)){
            Faculty faculty = facultyRepository.findById(id).orElse(null);
            facultyRepository.deleteById(id);
            return faculty;
        }
        return null;
    }

    public List<Faculty> findFacultiesByNameOrColor(String query) {
        logger.info("Was invoked method for find faculties by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(query, query);
    }
}