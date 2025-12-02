package com.coffeeshop.controller.admin;

import com.coffeeshop.model.Category;
import com.coffeeshop.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    // ========== LIST ==========
    @GetMapping("/admin/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/category/list";
    }

    // ========== ADD FORM ==========
    @GetMapping("/admin/categories/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/form";
    }

    // ========== EDIT FORM ==========
    @GetMapping("/admin/categories/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category/form";
    }


    // ========== SAVE (THE BLOCK THAT FIXES THE ERROR) ==========
    @PostMapping("/admin/categories/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        return "redirect:/admin/categories";
    }

    // ========== DELETE ==========
    @GetMapping("/admin/categories/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/admin/categories";
    }

}
