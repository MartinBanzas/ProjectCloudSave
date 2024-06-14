package com.martin.springboot.cloudsave.db_entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Setter
@Entity
@Table(name="juego")
public class Juego {

    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Getter
    @Column(name="Nombre")
    private String name;

    @Getter
    @Column(name="Sistema")
    private String Sistema;

    @Getter
    @Column(name="Directorio")
    private String Directorio;

    @Getter
    @Column(name="puntuacion")
    private int puntuacion;

    @Getter
    @Column(name = "terminado", columnDefinition = "TINYINT(1)")
    private boolean terminado;

    @Column(name = "fInicio")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fInicio;

    @Column(name = "fFin")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date fFin;


    @Getter
    @Column(name= "titulo_review", columnDefinition = "TEXT")
    private String tituloReview;

    @Getter
    @Column(name="review", columnDefinition = "TEXT")
    private String review;

    @Getter
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "juego", cascade={CascadeType.ALL})
    @JsonIgnore
    private List<Partida> listaPartidas;

    @Getter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Img img;


    public Juego() {}

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

    public void add(Partida tempPartida) {

        if (listaPartidas == null) {
            listaPartidas=new ArrayList<>();
        }
        listaPartidas.add(tempPartida);
        tempPartida.setJuego(this);
}


}
