package com.martin.springboot.cloudsave.json_schemas;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuegoAddDTO {

    private String name;
    private String Sistema;
    private String main;
    private String main_extra;
    private String completionist;
    private String company;
    private String year;

}


