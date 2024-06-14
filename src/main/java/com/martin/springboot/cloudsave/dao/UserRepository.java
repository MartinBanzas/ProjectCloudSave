package com.martin.springboot.cloudsave.dao;

import com.martin.springboot.cloudsave.db_entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<Users, String> {
}
