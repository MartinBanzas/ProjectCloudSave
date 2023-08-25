package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Partida;

import java.util.List;

public interface PartidaService {

    List<Partida> findAll();

    Partida findById(int theId);

    void save(Partida theSave);

    void deleteById(int theId);
}
