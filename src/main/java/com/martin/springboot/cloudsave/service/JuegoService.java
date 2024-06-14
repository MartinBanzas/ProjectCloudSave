package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.db_entities.Juego;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface JuegoService {

    Page<Juego> findAll(Pageable pageable);

    List<Juego> findAll();

    Juego findById(int theId);

    void save(Juego theGame);

    void deleteById(int theId);

    List<Juego> findByNombreContainingIgnoreCase(String nombre);
}
