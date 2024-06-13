package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Users;

import java.util.List;

public interface UserService {

    List<Users> findAll();
}