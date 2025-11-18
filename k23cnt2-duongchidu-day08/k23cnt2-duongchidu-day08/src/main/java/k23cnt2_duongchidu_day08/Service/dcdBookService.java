package k23cnt2_duongchidu_day08.service;

import k23cnt2_duongchidu_day08.entity.dcdBook;
import k23cnt2_duongchidu_day08.repository.dcdBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dcdBookService {

    @Autowired
    private dcdBookRepository bookRepository;

    public List<dcdBook> getAllBooks() {
        return bookRepository.findAll();
    }

    public dcdBook saveBook(dcdBook book) {
        return bookRepository.save(book);
    }

    public dcdBook getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
