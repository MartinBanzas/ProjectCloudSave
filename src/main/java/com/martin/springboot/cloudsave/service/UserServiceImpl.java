package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.dao.UserRepository;
import com.martin.springboot.cloudsave.db_entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

   public List<Users> findAll() {

        return userRepository.findAll();
    }
}
