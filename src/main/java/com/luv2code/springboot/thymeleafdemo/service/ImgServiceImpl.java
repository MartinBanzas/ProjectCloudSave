package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.ImgRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Img;
import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class ImgServiceImpl implements  ImgService {

    Path rutaImagen = Paths.get("./img");

    @Autowired
    EntityManager entityManager;

    @Autowired
    private JuegoService juegoService;
    @Autowired
    private ImgRepository imgRepository;
    @Override
    public Img store(int id, MultipartFile file) throws IOException {
        Juego juego = juegoService.findById(id);
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        Img img = new Img(fileName, file.getContentType());
        img.setJuego(juego);
        try {
            Files.copy(file.getInputStream(), this.rutaImagen.resolve(fileName
            ));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Ya hay archivo con ese nombre");
            }

    } return imgRepository.save(img);}




    public Resource load(String filename) {
        try {
            Path file = rutaImagen.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se ha podido leer el archivo");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
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
