package com.martin.springboot.cloudsave.controller;

import com.martin.springboot.cloudsave.db_entities.Users;
import com.martin.springboot.cloudsave.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/loginUser")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/loginUser")
    public String processLogin() {
        return "redirect:loginUser";
    }

    @GetMapping("/allUsers")
    public ResponseEntity <List<Users>> allUsers() {
        List <Users> users = userService.findAll();
        return ResponseEntity.ok(users);
    }



}
