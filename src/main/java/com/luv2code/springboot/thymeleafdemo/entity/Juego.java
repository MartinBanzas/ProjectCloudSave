package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="juego")
public class Juego {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="Nombre")
    private String name;

    @Column(name="Sistema")
    private String Sistema;

    @Column(name="Directorio")
    private String Directorio;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "juego", cascade={CascadeType.ALL})
    private List<Partida> listaPartidas;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
    private Img img;


//private LocalDateTime ultimaActu;

    public Juego() {}

    public Juego(String nombre, String sistema, String directorio) {
        this.name = nombre;
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

    public List<Partida> getListaPartidas() {
        return listaPartidas;
    }

    public void setListaPartidas(List<Partida> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }

    public void add(Partida tempPartida) {

        if (listaPartidas == null) {
            listaPartidas=new ArrayList<>();
        }
        listaPartidas.add(tempPartida);
        tempPartida.setJuego(this);
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSistema() {
        return Sistema;
    }

    public void setSistema(String sistema) {
        this.Sistema = sistema;
    }


    public String getDirectorio() {
        return Directorio;
    }

    public void setDirectorio(String directorio) {
        this.Directorio = directorio;
    }


}
