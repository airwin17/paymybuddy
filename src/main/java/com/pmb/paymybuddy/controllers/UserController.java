package com.pmb.paymybuddy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.exceptions.EmailAlreadyExistsException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.services.UserService;

@RestController( "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/saveUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok("User created");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /*@GetMapping("/getUserbyId/{id}")
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
    @PostMapping("/addConnection")
    public ResponseEntity<String> addConnection(@RequestParam String id1, @RequestParam String id2) {
        
        try {
            userService.addConnection(id1,id2);
            return ResponseEntity.ok("Connection added");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
    }*/
}
