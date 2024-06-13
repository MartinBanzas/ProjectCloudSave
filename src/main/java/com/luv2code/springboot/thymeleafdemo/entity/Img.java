package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Setter
@Getter
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Img {


    @Id
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Juego juego;
    private String name;
    private String type;

    public Img(String name, String type) {
        this.name = name;
        this.type = type;
    }


}
