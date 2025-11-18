package k23cnt2_duongchidu_day08.service;

import k23cnt2_duongchidu_day08.entity.dcdAuthor;

import java.util.List;

public interface dcdAuthorService {

    List<dcdAuthor> getAllAuthors();

    dcdAuthor getAuthorById(Long id);

    dcdAuthor save(dcdAuthor author);

    void delete(Long id);

    List<dcdAuthor> findAllById(List<Long> ids);
}
