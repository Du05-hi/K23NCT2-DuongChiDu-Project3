package k23cnt2_duongchidu_lab08_2.repository;

import k23cnt2_duongchidu_lab08_2.entity.BookAuthor;
import k23cnt2_duongchidu_lab08_2.entity.BookAuthorKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, BookAuthorKey> {

    @Query("SELECT ba.author.id FROM BookAuthor ba WHERE ba.book.id = :bookId")
    List<Long> findAuthorIdsByBookId(Long bookId);

    @Query("SELECT ba.author.id FROM BookAuthor ba WHERE ba.book.id = :bookId AND ba.isEditor = 1")
    Long findEditorIdByBookId(Long bookId);

    void deleteByBookId(Long bookId);
}
