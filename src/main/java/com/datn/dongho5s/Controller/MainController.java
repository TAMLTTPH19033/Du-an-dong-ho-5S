package com.datn.dongho5s.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/login")
    public String viewHomePage(){
        return "login";
    }

    @PostMapping("/login")
    public String viewLoginPage(){
        return "index";
    }
}
