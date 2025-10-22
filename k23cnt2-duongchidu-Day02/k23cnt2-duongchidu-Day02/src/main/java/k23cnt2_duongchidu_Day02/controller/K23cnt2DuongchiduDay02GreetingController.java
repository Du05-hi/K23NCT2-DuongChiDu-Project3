package k23cnt2_duongchidu_Day02.controller;

import k23cnt2_duongchidu_Day02.service.K23cnt2DuongchiduDay02GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class K23cnt2DuongchiduDay02GreetingController {

    private final K23cnt2DuongchiduDay02GreetingService greetingService;

    @Autowired
    public K23cnt2DuongchiduDay02GreetingController(K23cnt2DuongchiduDay02GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greet")
    public String greet() {
        return greetingService.greet("Dương Chí Du");
    }
}
