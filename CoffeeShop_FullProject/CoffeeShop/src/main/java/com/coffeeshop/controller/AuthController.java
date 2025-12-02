package com.coffeeshop.controller;

import com.coffeeshop.model.User;
import com.coffeeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User form,
                        HttpSession session,
                        Model model) {
        User user = userService.login(form.getEmail(), form.getPassword());
        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu");
            return "user/login";
        }
        session.setAttribute("currentUser", user);
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        try {
            userService.register(user);
            return "redirect:/login";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "user/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
