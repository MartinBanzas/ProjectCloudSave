package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.dao.PartidaRepository;
import com.martin.springboot.cloudsave.db_entities.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartidaServiceImpl implements PartidaService{

    private PartidaRepository partidaRepository;

    @Autowired
    public PartidaServiceImpl (PartidaRepository partidaRepository) {
        this.partidaRepository=partidaRepository;
    }
    @Override
    public List<Partida> findAll() {
       return partidaRepository.findAll();
    }

    @Override
    public Partida findById(int theId) {

        Optional<Partida> result = partidaRepository.findById(theId);

        Partida thePartida =null;
        if (result.isPresent()) {
            thePartida = result.get();
        }
        else {
            throw new RuntimeException("No se ha encontrado el juego con id - " + theId);
        }

        return thePartida;
    }



    @Override
    public void save(Partida theSave) {

        partidaRepository.save(theSave);
    }

    @Override
    public void deleteById(int theId) {

        partidaRepository.deleteById(theId);
    }
}
