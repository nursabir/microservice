package com.javastart.user.controller;

import com.javastart.user.dto.UserRequestDTO;
import com.javastart.user.dto.UserResponseDTO;
import com.javastart.user.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/get/{userId}")
    public UserResponseDTO getUser(@PathVariable Long userId) {
        return UserResponseDTO.to(userService.getUserById(userId));
    }

    @PostMapping(value = "/users")
    public Long addUser(@RequestBody UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO.getName(), userRequestDTO.getSurname(), userRequestDTO.getPhone(), userRequestDTO.getEmail());
    }




}
