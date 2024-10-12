package com.pmb.paymybuddy.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.exceptions.ActionNotAllowed;
import com.pmb.paymybuddy.exceptions.ConnectionAlreadyExistException;
import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "Create a new user")
    @PostMapping("/saveUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("User created");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Operation(summary = "Update user information")
    @PutMapping("/updateUser")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @AuthenticationPrincipal User logedUser) {
        userService.updateUser(user, logedUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @Operation(summary = "Add a new connection between 2 users")
    @PostMapping("/addConnection")
    public ResponseEntity<HttpStatus> addConnection(@RequestParam String email,
            @AuthenticationPrincipal User logedUser) {
        try {
            userService.addConnection(email, logedUser);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ActionNotAllowed e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ConnectionAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
