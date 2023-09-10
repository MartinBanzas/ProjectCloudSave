package com.luv2code.springboot.thymeleafdemo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*Spring Boot hace solicitudes diferentes si se trata de acceder al forms desde el botón de la navbar
a si llama a ese mismo forms en el momento que se trata de acceder a un lugar restringido, una es POST y otra GET
 y ambas son incompatibles, por lo que hay que tener dos mapeos*/

@Controller
public class LoginController {

    @GetMapping("/loginUser")
    public String showLoginForm() {
        return "login";
    }


    @PostMapping("/loginUser")
    public String processLogin() {
        return "redirect:loginUser";
    }


}
