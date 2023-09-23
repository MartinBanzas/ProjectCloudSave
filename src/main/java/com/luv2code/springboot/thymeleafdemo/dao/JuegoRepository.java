package com.luv2code.springboot.thymeleafdemo.dao;

import com.luv2code.springboot.thymeleafdemo.entity.Juego;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {

    public List<Juego> findAllByOrderByNameAsc();

    public List<Juego> findByNameContainingIgnoreCase(String nombre);

    public Page<Juego> findAllByOrderByNameAsc(Pageable pageable);
}
