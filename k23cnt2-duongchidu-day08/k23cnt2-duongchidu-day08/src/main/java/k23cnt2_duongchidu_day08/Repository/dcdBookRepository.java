package k23cnt2_duongchidu_day08.repository;

import k23cnt2_duongchidu_day08.entity.dcdBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface dcdBookRepository extends JpaRepository<dcdBook, Long> {
}
