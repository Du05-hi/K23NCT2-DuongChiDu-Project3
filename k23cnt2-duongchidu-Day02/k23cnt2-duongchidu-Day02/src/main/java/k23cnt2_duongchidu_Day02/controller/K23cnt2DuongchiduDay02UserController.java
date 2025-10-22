package k23cnt2_duongchidu_Day02.controller;

import k23cnt2_duongchidu_Day02.service.K23cnt2DuongchiduDay02UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class K23cnt2DuongchiduDay02UserController {

    private final K23cnt2DuongchiduDay02UserService userService;

    @Autowired
    public K23cnt2DuongchiduDay02UserController(K23cnt2DuongchiduDay02UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUser() {
        return userService.getUserDetails();
    }

    @GetMapping("/users")
    public String getUsers() {
        return "<h1>Get all users</h1>";
    }

    @PostMapping("/users")
    public String createUser() {
        return "<h1>User created</h1>";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable int id) {
        return "<h1>User with ID " + id + " updated</h1>";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return "<h1>User with ID " + id + " deleted</h1>";
    }
}
