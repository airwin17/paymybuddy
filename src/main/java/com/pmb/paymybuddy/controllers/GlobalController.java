package com.pmb.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {
    @GetMapping("/login")
    public String login() {
    return "/html/login.html";
    }

    @GetMapping("/signin")
    public String singnin() {
        return "/html/signin.html";
    }

}
