package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.dao.JuegoRepository;
import com.martin.springboot.cloudsave.db_entities.Juego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoServiceImpl  implements JuegoService {

   @Autowired
   final private JuegoRepository juegoRepository;


    public JuegoServiceImpl (JuegoRepository juegoRepository) {
        this.juegoRepository=juegoRepository;
    }

    @Override
    public List<Juego> findAll() {
        return juegoRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Juego findById(int theId) {
        Optional<Juego> result = juegoRepository.findById(theId);
        Juego theGame = null;

        if (result.isPresent()) {
            theGame = result.get();
        }
        else {
            throw new RuntimeException("No se ha encontrado el juego con id - " + theId);
        }

        return theGame;
    }

    @Override
    public void save(Juego theGame) {
        juegoRepository.save(theGame);
    }

    @Override
    public void deleteById(int theId) {

        juegoRepository.deleteById(theId);
    }

    @Override
    public List<Juego> findByNombreContainingIgnoreCase(String nombre) {
        return juegoRepository.findByNameContainingIgnoreCase(nombre);
    }

    @Override
    public Page<Juego> findAll(Pageable pageable) {
        return juegoRepository.findAllByOrderByNameAsc(pageable);
    }
}