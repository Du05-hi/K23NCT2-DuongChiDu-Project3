package k23cnt2_duongchidu_day08.controller;

import k23cnt2_duongchidu_day08.entity.dcdAuthor;
import k23cnt2_duongchidu_day08.entity.dcdBook;
import k23cnt2_duongchidu_day08.service.dcdAuthorService;
import k23cnt2_duongchidu_day08.service.dcdBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class dcdBookController {

    @Autowired
    private dcdBookService bookService;

    @Autowired
    private dcdAuthorService authorService;

    private static final String UPLOAD_DIR = "src/main/resources/static/";
    private static final String UPLOAD_PathFile = "images/products/";

    // Hiển thị toàn bộ sách
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/book-list";
    }

    // Form thêm mới sách
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new dcdBook());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    // Lưu sách (create / update)
    @PostMapping("/new")
    public String saveBook(@ModelAttribute("book") dcdBook book,
                           @RequestParam List<Long> authorIds,
                           @RequestParam("imageBook") MultipartFile imageFile) {

        if (!imageFile.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR + UPLOAD_PathFile);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String fileExtension = "";

                int dotIndex = originalFileName.lastIndexOf(".");
                if (dotIndex > 0) {
                    fileExtension = originalFileName.substring(dotIndex);
                }

                String newFileName = book.getCode() + fileExtension;
                Path filePath = uploadPath.resolve(newFileName);

                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                book.setImgUrl("/" + UPLOAD_PathFile + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<dcdAuthor> authors = new ArrayList<>(authorService.findAllById(authorIds));
        book.setAuthors(authors);

        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        dcdBook book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/book-form";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
