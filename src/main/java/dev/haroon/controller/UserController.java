package dev.haroon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.haroon.dto.UserDTO;
import dev.haroon.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping("/register")
    public ResponseEntity<Integer> registerUser(@RequestBody UserDTO userDTO) {
        Integer userId = userService.registerUser(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody UserDTO userDTO) {
        Integer userId = userService.loginUser(userDTO);
        return ResponseEntity.ok(true);
    }

    // Update User
    @PutMapping("/update")
    public ResponseEntity<Integer> updateUser(@RequestBody UserDTO userDTO) {
        Integer userId = userService.updateUser(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    // Delete User
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestBody UserDTO userDTO) {
        userService.deleteUser(userDTO);
        return ResponseEntity.ok(true);
    }
}
