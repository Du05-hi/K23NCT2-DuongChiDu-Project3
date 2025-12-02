package com.coffeeshop.service;

import com.coffeeshop.model.User;
import com.coffeeshop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Đăng ký
    public User register(User user) {
        if (!StringUtils.hasText(user.getEmail()) ||
                !StringUtils.hasText(user.getPassword())) {
            throw new IllegalArgumentException("Email và mật khẩu không được bỏ trống");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        user.setRole("USER");
        return userRepository.save(user);
    }

    // Đăng nhập
    public User login(String email, String password) {
        User u = userRepository.findByEmail(email);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }
}
