package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Users;
import com.luv2code.springboot.thymeleafdemo.service.UserService;
import com.luv2code.springboot.thymeleafdemo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*Spring Boot hace solicitudes diferentes si se trata de acceder al forms desde el botón de la navbar
a si llama a ese mismo forms en el momento que se trata de acceder a un lugar restringido, una es POST y otra GET
 y ambas son incompatibles, por lo que hay que tener dos mapeos*/

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
