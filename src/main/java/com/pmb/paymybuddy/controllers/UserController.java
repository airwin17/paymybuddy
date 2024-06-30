package com.pmb.paymybuddy.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.exceptions.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.UserService;

@RestController
public class UserController {
    private UserService userService;
    
    public UserController() {
        this.userService =new UserService();;
    }
    @PostMapping("/saveUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok("User created");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/getUserbyId/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        Optional<User> user = userService.findUserById(id);
        if(user.isPresent()) 
            return ResponseEntity.ok(user.get());
        else
            return ResponseEntity.notFound().build();
    }
    @GetMapping("/getUserByUsername/{username}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("username") String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if(user.isPresent()) 
            return ResponseEntity.ok(user.get());
        else
            return ResponseEntity.notFound().build();
    }
    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return ResponseEntity.ok("User updated");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String id) {
        try{
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted");
        }catch(UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
