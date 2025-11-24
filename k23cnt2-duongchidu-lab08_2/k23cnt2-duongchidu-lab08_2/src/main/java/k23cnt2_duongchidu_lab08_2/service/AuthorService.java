package k23cnt2_duongchidu_lab08_2.service;

import k23cnt2_duongchidu_lab08_2.entity.Author;
import k23cnt2_duongchidu_lab08_2.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository repo;

    public AuthorService(AuthorRepository repo){
        this.repo = repo;
    }

    public List<Author> findAll(){ return repo.findAll(); }
    public Author findById(Long id){ return repo.findById(id).orElse(null); }
    public Author save(Author a){ return repo.save(a); }
    public void delete(Long id){ repo.deleteById(id); }
}
