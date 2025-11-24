package k23cnt2_duongchidu_lab08_2.repository;

import k23cnt2_duongchidu_lab08_2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
