package com.coffeeshop.controller;

import com.coffeeshop.model.User;
import com.coffeeshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // ==================== FORM ĐĂNG NHẬP ====================
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    // ==================== XỬ LÝ ĐĂNG NHẬP ====================
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User form,
                        HttpSession session,
                        Model model) {

        // Gọi service kiểm tra email + mật khẩu
        User user = userService.login(form.getEmail(), form.getPassword());

        if (user == null) {
            model.addAttribute("error", "Sai email hoặc mật khẩu!");
            return "user/login";
        }

        // Nếu tài khoản bị khóa
        if (user.getStatus() != null && user.getStatus() == 0) {
            model.addAttribute("error", "Tài khoản của bạn đã bị khóa!");
            return "user/login";
        }

        // Lưu user vào SESSION
        session.setAttribute("currentUser", user);

        // Chuyển hướng theo role
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin";
        }

        return "redirect:/";
    }

    // ==================== FORM ĐĂNG KÝ ====================
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    // ==================== XỬ LÝ ĐĂNG KÝ ====================
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

    // ==================== ĐĂNG XUẤT ====================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session
        return "redirect:/";
    }
}
