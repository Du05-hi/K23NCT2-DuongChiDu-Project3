package k23cnt2_duongchidu_lab08_2.controller;

import k23cnt2_duongchidu_lab08_2.entity.Book;
import k23cnt2_duongchidu_lab08_2.service.AuthorService;
import k23cnt2_duongchidu_lab08_2.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "book/add";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute Book book,
            @RequestParam List<Long> authorIds,
            @RequestParam(required = false) Long editor) {

        bookService.saveBook(book, authorIds, editor);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Book b = bookService.findById(id);

        model.addAttribute("book", b);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("selectedAuthors", bookService.getAuthorIds(id));
        model.addAttribute("editorId", bookService.getEditorId(id));

        return "book/edit";
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute Book book,
            @RequestParam List<Long> authorIds,
            @RequestParam(required = false) Long editor) {

        bookService.updateBook(book, authorIds, editor);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
