package k23cnt2_duongchidu_day06.service;

import k23cnt2_duongchidu_day06.dto.StudentDTO;
import k23cnt2_duongchidu_day06.entity.Student;
import k23cnt2_duongchidu_day06.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Optional<StudentDTO> findById(Long id) {
        return repo.findById(id)
                .map(s -> new StudentDTO(
                        s.getId(),
                        s.getName(),
                        s.getEmail(),
                        s.getAge()
                ));
    }

    public boolean save(StudentDTO dto) {
        try {
            Student s = new Student();
            s.setName(dto.getName());
            s.setEmail(dto.getEmail());
            s.setAge(dto.getAge());
            repo.save(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Student updateStudentById(Long id, StudentDTO dto) {
        return repo.findById(id)
                .map(s -> {
                    s.setName(dto.getName());
                    s.setEmail(dto.getEmail());
                    s.setAge(dto.getAge());
                    return repo.save(s);
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void deleteStudent(Long id) {
        repo.deleteById(id);
    }
}
