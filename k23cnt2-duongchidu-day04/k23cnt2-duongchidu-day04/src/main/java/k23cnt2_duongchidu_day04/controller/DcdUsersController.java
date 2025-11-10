package k23cnt2_duongchidu_day04.controller;

import k23cnt2_duongchidu_day04.dto.DcdUsersDTO;
import k23cnt2_duongchidu_day04.entity.DcdUsers;
import k23cnt2_duongchidu_day04.service.DcdUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
public class DcdUsersController {

    @Autowired
    private DcdUsersService usersService;

    @GetMapping("/user-list")
    public List<DcdUsers> getAllUsers() {
        return usersService.findAll();
    }

    @PostMapping("/user-add")
    public ResponseEntity<String> addUser(@Valid @RequestBody DcdUsersDTO user) {
        usersService.create(user);
        return ResponseEntity.ok("User created successfully");
    }
}
