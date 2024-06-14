package com.martin.springboot.cloudsave.dao;

import com.martin.springboot.cloudsave.db_entities.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
}
