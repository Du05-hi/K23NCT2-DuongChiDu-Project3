package k23cnt2_duongchidu_lab08_2.service;

import k23cnt2_duongchidu_lab08_2.entity.*;
import k23cnt2_duongchidu_lab08_2.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;
    private final BookAuthorRepository baRepo;

    public BookService(BookRepository bookRepo, AuthorRepository authorRepo, BookAuthorRepository baRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.baRepo = baRepo;
    }

    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    public Book findById(Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    public void saveBook(Book book, List<Long> authorIds, Long editorId) {

        bookRepo.save(book);

        baRepo.deleteByBookId(book.getId());

        for (Long aid : authorIds) {
            Author a = authorRepo.findById(aid).orElse(null);
            if (a == null) continue;

            int editor = (editorId != null && editorId.equals(aid)) ? 1 : 0;

            BookAuthor ba = new BookAuthor(book, a, editor);
            baRepo.save(ba);
        }
    }

    public void updateBook(Book book, List<Long> authorIds, Long editorId) {

        bookRepo.save(book);

        baRepo.deleteByBookId(book.getId());

        for (Long aid : authorIds) {
            Author a = authorRepo.findById(aid).orElse(null);
            if (a == null) continue;

            int editor = (editorId != null && editorId.equals(aid)) ? 1 : 0;

            BookAuthor ba = new BookAuthor(book, a, editor);
            baRepo.save(ba);
        }
    }

    public void delete(Long id) {
        baRepo.deleteByBookId(id);
        bookRepo.deleteById(id);
    }

    public List<Long> getAuthorIds(Long bookId) {
        return baRepo.findAuthorIdsByBookId(bookId);
    }

    public Long getEditorId(Long bookId) {
        return baRepo.findEditorIdByBookId(bookId);
    }
}
