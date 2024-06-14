package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.db_entities.Partida;

import java.util.List;

public interface PartidaService {

    List<Partida> findAll();

    Partida findById(int theId);

    void save(Partida theSave);

    void deleteById(int theId);
}
