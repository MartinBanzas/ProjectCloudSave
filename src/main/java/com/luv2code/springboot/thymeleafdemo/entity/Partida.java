package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="juegoid")
    private Juego juego;


    @Column(name="descripcion")
    private String descripcion;

    @Column(name="ruta")
    private String rutaarchivo;


    public Partida () {}

    public Partida(Juego juego, String descripcion) {
        this.juego = juego;

        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Juego getJuego() {
        return juego;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setJuego(Juego juego) {
        this.juego=juego;
    }

    public String getRutaarchivo() {
        return rutaarchivo;
    }

    public void setRutaarchivo(String rutaarchivo) {
        this.rutaarchivo = rutaarchivo;
    }

  /*  public String getUuid() {
        return uuid;
    }

    public void setUuid() {
        UUID timestampBasedUUID = new UUID(System.currentTimeMillis(), 0L);
        uuid = timestampBasedUUID.toString();
    }*/
}
