package k23cnt2_duongchidu_day08.controller;

import k23cnt2_duongchidu_day08.entity.dcdAuthor;
import k23cnt2_duongchidu_day08.service.dcdAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class dcdAuthorController {

    @Autowired
    private dcdAuthorService authorService;

    // Danh sách tác gia
    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors/author-list";
    }

    // Form thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new dcdAuthor());
        return "authors/author-form";
    }

    // Lưu tác giả
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") dcdAuthor author) {
        authorService.save(author);
        return "redirect:/authors";
    }

    // Form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        dcdAuthor author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "authors/author-form";
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return "redirect:/authors";
    }
}
