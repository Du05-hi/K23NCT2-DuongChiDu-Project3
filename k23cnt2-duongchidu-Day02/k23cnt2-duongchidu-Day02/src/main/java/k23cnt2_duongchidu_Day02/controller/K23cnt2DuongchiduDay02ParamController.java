package k23cnt2_duongchidu_Day02.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class K23cnt2DuongchiduDay02ParamController {

    @GetMapping("/my-param")
    public String searchUsers(@RequestParam(value = "name", required = false) String name) {
        if (name == null)
            return "<h2>No name provided, returning all users</h2>";
        return "<h1>Searching for user: " + name + "</h1>";
    }

    @GetMapping("/my-variable/{id}")
    public String getUserById(@PathVariable String id) {
        return "<h1>User ID is " + id + "</h1>";
    }
}
