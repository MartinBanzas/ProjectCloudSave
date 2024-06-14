package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.db_entities.Users;

import java.util.List;

public interface UserService {

    List<Users> findAll();
}