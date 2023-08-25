package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.ImgRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Img;
import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class ImgServiceImpl implements  ImgService {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private JuegoService juegoService;
    @Autowired
    private ImgRepository imgRepository;
    @Override
    public Img store(int id, MultipartFile file) throws IOException {
        Juego juego = null;

        juego=juegoService.findById(id);
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        Img img = new Img(fileName, file.getContentType(), file.getBytes());
        img.setJuego(juego);
        System.out.println(juego.getName()+ juego.getId());
        return imgRepository.save(img);
    }

    @Override
    public Img getImg(int id) {
        return imgRepository.findById(id).get();
    }

    @Override
    public Stream<Img> getAllImg() {
        return imgRepository.findAll().stream();
    }

    @Override
    public Img findById(int id) {
        Optional<Img> optionalImg = imgRepository.findById(id);

        // Verificar si el objeto existe en la base de datos
        if (optionalImg.isPresent()) {
            return optionalImg.get();
        } else {
            throw new NoSuchElementException("Img with ID " + id + " not found");
        }
    }
}
