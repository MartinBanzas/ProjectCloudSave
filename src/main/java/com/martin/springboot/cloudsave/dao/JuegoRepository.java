package com.martin.springboot.cloudsave.dao;

import com.martin.springboot.cloudsave.db_entities.Juego;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Integer> {

     List<Juego> findAllByOrderByNameAsc();
     List<Juego> findByNameContainingIgnoreCase(String nombre);
     Page<Juego> findAllByOrderByNameAsc(Pageable pageable);
}
