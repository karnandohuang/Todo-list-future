package com.blibli.ProjectTodoList.controller;

import com.blibli.ProjectTodoList.exception.ResourceNotFoundException;
import com.blibli.ProjectTodoList.model.User;
import com.blibli.ProjectTodoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

//@Api(value = "User Controller")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/{userId}")
    public Optional<User> getUserById(@PathVariable Long userId){
        return userRepository.findById(userId);
    }

    @PostMapping("/api/user")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/api/user/{userId}")
    public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest){
        return userRepository.findById(userId)
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setUsername(userRequest.getUsername());
                    return userRepository.save(user);
                }).orElseThrow(()-> new ResourceNotFoundException("Username not found with id " + userId));
    }

    @DeleteMapping("/api/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId)
    {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Username not found with id " + userId));
    }

}

