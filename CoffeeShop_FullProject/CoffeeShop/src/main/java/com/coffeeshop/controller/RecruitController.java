package com.coffeeshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecruitController {

    @GetMapping("/recruit")
    public String recruitPage() {
        return "user/recruit";
    }
}
