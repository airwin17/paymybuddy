package com.pmb.paymybuddy.controllers;

import java.util.List;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;
import com.pmb.paymybuddy.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@Controller
public class GlobalController {
    
    private TransactionService transactionService;
    private UserService userService;
    public GlobalController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }
    @Operation(summary = "return Login page")
    @GetMapping("/loginPage")
    public String login() {
    return "views/login.html";
    }
    @Operation(summary = "return Signin page")
    @GetMapping("/signin")
    public String singnin() {
        return "views/signin.html";
    }
    @Operation(summary = "return relationship page")
    @GetMapping("/addRelationship")
    public String ajouterUneRelation() {
        return "views/addRelationship.html";
    }
    @Operation(summary = "return transaction page")
    @GetMapping("/addTransaction")
    public String ajouterUneTransaction(Model model,@AuthenticationPrincipal User user) {
        user=userService.loadConnectionForUser(user);
        List<UserDto> userDtos = userService.getConnectedUser(user.getConnectedUser());
        userDtos.add(new UserDto(user.getUsername(), user.getEmail()));
        model.addAttribute("transactionsDTO", transactionService.getTransactionsForView(user));
        model.addAttribute("UserDto",userDtos );
        model.addAttribute("credit", user.getBankAcount().getBalance());
        return "views/addTransaction.html";
    }
    @Operation(summary = "return profil page")
    @GetMapping("/profil")
    public String profil(Model model,@AuthenticationPrincipal User user) {
        System.out.println("profilactivated");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "views/editProfil.html";
    }
    @Operation(summary = "return home page")
    @GetMapping("/")
    public String home(Model model,@AuthenticationPrincipal User user) {
        return "redirect:/addTransaction";
    }
}
