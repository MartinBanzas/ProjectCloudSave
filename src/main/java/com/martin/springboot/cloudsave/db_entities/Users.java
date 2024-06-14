package com.martin.springboot.cloudsave.db_entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    private String username;
    private String password;
}
