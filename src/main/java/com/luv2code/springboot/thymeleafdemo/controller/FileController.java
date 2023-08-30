package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Juego;
import com.luv2code.springboot.thymeleafdemo.entity.Partida;
import com.luv2code.springboot.thymeleafdemo.service.FileStorageService;
import com.luv2code.springboot.thymeleafdemo.service.JuegoService;
import com.luv2code.springboot.thymeleafdemo.service.PartidaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
@RequestMapping("/juegos")
public class FileController {

    Path root = Paths.get("./uploads");

    FileStorageService storageService;

    private JuegoService juegoService;

    private PartidaService partidaService;


    @Autowired
    public FileController(JuegoService juegoService, PartidaService partidaService, FileStorageService storageService) {
        this.juegoService = juegoService;
        this.partidaService = partidaService;
        this.storageService=storageService;
    }


    @GetMapping("/files/new")
    public String newFile(Model model) {
        return "juegos/uploadSave";
    }

    @PostMapping("/files/upload")
    @Transactional
    public String uploadFile(Model model,
                             @RequestParam("id") int id,
                             @RequestParam("descripcion") String descripcion,
                             @RequestParam("file") MultipartFile file) {
        String message = "";

        Partida newPartida = new Partida();
        newPartida.setDescripcion(descripcion);
        Juego theGame = juegoService.findById(id);
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = generateUniqueFileName(originalFileName);

        newPartida.setRutaarchivo(uniqueFileName);
        newPartida.setJuego(theGame);
        partidaService.save(newPartida);

        try {
            storageService.save(file, uniqueFileName);

            message = "Se ha guardado el fichero con éxito: " + uniqueFileName;
            model.addAttribute("message", message);
        } catch (Exception e) {
            message = "No se ha podido guardar el fichero: " + uniqueFileName + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
        }
        return "redirect:/juegos/lista";
    }

    private String generateUniqueFileName(String originalFileName) {

        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
        String uniqueFileName = baseName + "." + extension;

        int count = 1;
        while (Files.exists(this.root.resolve(uniqueFileName))) {
            uniqueFileName = baseName + "(" + count + ")." + extension;
            count++;
        }

        return uniqueFileName;
    }



    @PostMapping("/files/delete/{id}")
    @Transactional
    public String deleteFile(@PathVariable int id) {

        Partida partida = partidaService.findById(id);
        Resource file = storageService.load(partida.getRutaarchivo());
        storageService.delete(file.getFilename());
        partidaService.deleteById(id);

return "redirect:/juegos/lista";

    }


        @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable int id) {

        Partida partida = partidaService.findById(id);

        Resource file = storageService.load(partida.getRutaarchivo());


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
