package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private String username;
    private String password;
}
