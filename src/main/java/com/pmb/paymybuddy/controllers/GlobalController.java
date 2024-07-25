package com.pmb.paymybuddy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.TransactionService;
import com.pmb.paymybuddy.services.UserService;

@Controller
public class GlobalController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
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
        user=userService.loadConnectionForUser(user);
        List<UserDto> userDtos = userService.getConnectedUser(user.getConnectedUser());
        userDtos.add(new UserDto(user.getUsername(), user.getEmail()));
        model.addAttribute("transactionsDTO", transactionService.getTransactionsForView(user));
        model.addAttribute("UserDto",userDtos );
        model.addAttribute("credit", user.getBankAcount().getBalance());
        return "/views/addTransaction.html";
    }
    @GetMapping("/profil")
    public String profil(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "/views/editProfil.html";
    }
}
