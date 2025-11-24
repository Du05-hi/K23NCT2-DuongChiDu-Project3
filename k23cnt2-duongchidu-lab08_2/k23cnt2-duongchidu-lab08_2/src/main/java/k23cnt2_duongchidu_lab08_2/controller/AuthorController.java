package k23cnt2_duongchidu_lab08_2.controller;

import k23cnt2_duongchidu_lab08_2.entity.Author;
import k23cnt2_duongchidu_lab08_2.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service){
        this.service = service;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("authors", service.findAll());
        return "author/index";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("author", new Author());
        return "author/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Author author){
        service.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "redirect:/authors";
    }
}
