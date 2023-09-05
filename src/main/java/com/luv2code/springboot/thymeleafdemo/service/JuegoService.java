package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Juego;

import java.util.List;


public interface JuegoService {

    List<Juego> findAll();

    Juego findById(int theId);

    void save(Juego theGame);

    void deleteById(int theId);

    List<Juego> findByNombreContainingIgnoreCase(String nombre);
}
