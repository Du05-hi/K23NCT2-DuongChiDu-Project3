package com.coffeeshop.controller;

import com.coffeeshop.model.User;
import com.coffeeshop.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================== HIỂN THỊ TRANG HỒ SƠ ==================
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {

        User current = (User) session.getAttribute("currentUser");
        if (current == null) {
            return "redirect:/login";
        }

        // lấy lại từ DB cho chắc
        User dbUser = userRepository.findById(current.getId())
                .orElse(current);

        model.addAttribute("user", dbUser);
        return "user/profile";
    }

    // ================== CẬP NHẬT THÔNG TIN CƠ BẢN ==================
    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") User formUser,
            HttpSession session,
            Model model
    ) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) {
            return "redirect:/login";
        }

        User dbUser = userRepository.findById(current.getId())
                .orElse(null);

        if (dbUser == null) {
            return "redirect:/login";
        }

        // Cho phép sửa họ tên thôi (email giữ nguyên)
        dbUser.setFullName(formUser.getFullName());

        userRepository.save(dbUser);
        session.setAttribute("currentUser", dbUser);

        model.addAttribute("user", dbUser);
        model.addAttribute("msgProfile", "Cập nhật thông tin thành công!");

        return "user/profile";
    }

    // ================== ĐỔI MẬT KHẨU ==================
    @PostMapping("/profile/change-password")
    public String changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            Model model
    ) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) {
            return "redirect:/login";
        }

        User dbUser = userRepository.findById(current.getId())
                .orElse(null);

        if (dbUser == null) {
            return "redirect:/login";
        }

        // kiểm tra
        if (!dbUser.getPassword().equals(oldPassword)) {
            model.addAttribute("errorPassword", "Mật khẩu cũ không đúng!");
        } else if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorPassword", "Xác nhận mật khẩu không khớp!");
        } else if (newPassword == null || newPassword.trim().length() < 4) {
            model.addAttribute("errorPassword", "Mật khẩu mới phải từ 4 ký tự trở lên!");
        } else {
            // OK -> cập nhật
            dbUser.setPassword(newPassword);
            userRepository.save(dbUser);
            session.setAttribute("currentUser", dbUser);
            model.addAttribute("msgPassword", "Đổi mật khẩu thành công!");
        }

        model.addAttribute("user", dbUser);
        return "user/profile";
    }
}
