package com.pmb.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {
    @GetMapping("/loginPage")
    public String login() {
    return "/views/login.html";
    }

    @GetMapping("/signin")
    public String singnin() {
        return "/views/signin.html";
    }
    @GetMapping("/home")
    public String home() {
        System.out.println("home");
        return "/views/home.html";
    }
    @GetMapping("/addRelationship")
    public String ajouterUneRelation() {
        return "/views/addRelationship.html";
    }

    @GetMapping("/addTransaction")
    public String ajouterUneTransaction() {
        return "/views/addTransaction.html";
    }

    @GetMapping("/profil")
    public String profil(Model model) {
        model.addAttribute("username", "test");
        model.addAttribute("email", "test");
        System.out.println("profil");
        return "/views/editProfil.html";
    }

}
