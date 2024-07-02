package com.martin.springboot.cloudsave.db_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="juego")
public class Juego {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY) @Column(name="id") private int id;
    @Column(name="Nombre") private String name;
    @Column(name="Sistema") private String Sistema;
    @Column(name="Directorio") private String Directorio;
    @Column(name="puntuacion") private int puntuacion;
    @Column(name = "terminado", columnDefinition = "TINYINT(1)") private boolean terminado;
    @Column(name = "fInicio") @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "dd/MM/yyyy") private Date fInicio;
    @Column(name = "fFin") @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "dd/MM/yyyy") private Date fFin;
    @Column(name= "titulo_review", columnDefinition = "TEXT") private String tituloReview;
    @Column(name="review", columnDefinition = "TEXT") private String review;
    @Column(name="img_Path") private String imgPath;
    @Column(name="main") private String main;
    @Column(name="main_extra") private String main_extra;
    @Column(name="completionist") private String completionist;
    @Column(name="company") private String company;
    @Column(name="year") private String year;

   // @JsonIgnore @OneToMany(fetch = FetchType.LAZY,mappedBy = "juego", cascade={CascadeType.ALL}) private List<Partida> listaPartidas;

    public Juego(String name, String sistema, String directorio) {
        this.name = name;
        this.Sistema = sistema;
        this.Directorio = directorio;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id +
                ", nombre='" + name + '\'' +
                ", sistema='" + Sistema + '\'' +
                ", directorio='" + Directorio + '\'' +
                ", wiki='"  + '\'' +
                ", ultimaActu="  +
                '}';
    }
}