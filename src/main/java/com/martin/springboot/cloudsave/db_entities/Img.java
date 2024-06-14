package com.martin.springboot.cloudsave.db_entities;

import jakarta.persistence.*;
import lombok.*;


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
