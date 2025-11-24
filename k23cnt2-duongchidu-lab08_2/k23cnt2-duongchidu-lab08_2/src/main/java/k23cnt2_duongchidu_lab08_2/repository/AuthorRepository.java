package k23cnt2_duongchidu_lab08_2.repository;

import k23cnt2_duongchidu_lab08_2.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
