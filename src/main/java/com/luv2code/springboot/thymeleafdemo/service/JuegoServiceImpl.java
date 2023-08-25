package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.JuegoRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoServiceImpl  implements JuegoService {

    private JuegoRepository juegoRepository;

    @Autowired
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
}
