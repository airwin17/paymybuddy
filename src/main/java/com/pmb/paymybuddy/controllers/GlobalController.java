package com.pmb.paymybuddy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;

@Controller
public class GlobalController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping("/loginPage")
    public String login() {
    return "/views/login.html";
    }

    @GetMapping("/signin")
    public String singnin() {
        return "/views/signin.html";
    }
    @GetMapping("/addRelationship")
    public String ajouterUneRelation() {
        return "/views/addRelationship.html";
    }
    @GetMapping("/addTransaction")
    public String ajouterUneTransaction(Model model,@AuthenticationPrincipal User user) {
        //int=user.getConnectedUser().stream().map(i->i.getUsername()).toList();
        model.addAttribute("transactions", transactionService.findTransactionsForView(user));
        model.addAttribute("connections", user.getConnectedUser().stream().map(i->i.getUsername()).toList());
        return "/views/addTransaction.html";
    }
    @GetMapping("/profil")
    public String profil(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "/views/editProfil.html";
    }
}
