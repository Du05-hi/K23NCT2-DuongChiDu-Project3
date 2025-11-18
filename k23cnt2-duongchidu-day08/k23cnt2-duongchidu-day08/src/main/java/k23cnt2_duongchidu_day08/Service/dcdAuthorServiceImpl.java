package k23cnt2_duongchidu_day08.service;

import k23cnt2_duongchidu_day08.entity.dcdAuthor;
import k23cnt2_duongchidu_day08.repository.dcdAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dcdAuthorServiceImpl implements dcdAuthorService {

    @Autowired
    private dcdAuthorRepository authorRepository;

    @Override
    public List<dcdAuthor> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public dcdAuthor getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public dcdAuthor save(dcdAuthor author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<dcdAuthor> findAllById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }
}
