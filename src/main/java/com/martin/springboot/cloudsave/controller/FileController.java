package com.martin.springboot.cloudsave.controller;

import com.martin.springboot.cloudsave.db_entities.Juego;
import com.martin.springboot.cloudsave.response.ResponseMessage;
import com.martin.springboot.cloudsave.service.FileStorageService;
import com.martin.springboot.cloudsave.service.JuegoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/juegos")
@CrossOrigin(origins="http://localhost:3000")
public class FileController {

    private final Path root = Paths.get("./uploads");


    @Autowired
    private  FileStorageService storageService;

    @Autowired
    private JuegoService juegoService;





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


    //Borrar partidas
    @PostMapping("/files/delete/{id}")
    @Transactional
    public String deleteFile(@PathVariable int id) {

      //  Partida partida = partidaService.findById(id);
       // Resource file = storageService.load(partida.getRutaarchivo());
      //  storageService.delete(file.getFilename());
      //  partidaService.deleteById(id);

return "redirect:/juegos/lista";

    }

    //Descarga de partidas
    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable int id) {
       // Partida partida = partidaService.findById(id);
       // Resource file = storageService.load(partida.getRutaarchivo());
        return ResponseEntity.ok(null);
    }

    //Subida de carátulas
    @PostMapping("/img/uploadImg")
    public ResponseEntity<ResponseMessage> uploadImg(@RequestParam("id") int id, @RequestParam("img") MultipartFile file) {
        String message;
        Juego theGame = juegoService.findById(id);

        theGame.setImgPath(file.getOriginalFilename());
        try {
            storageService.saveImg(file);
            message = "File uploaded successfully: " + theGame.getImgPath();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + theGame.getImgPath() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    //Obtener las carátulas de la página principal
    @GetMapping("/img/{id}")
    public ResponseEntity<Resource> getImg(@PathVariable int id) {

        Juego juego = juegoService.findById(id);
        String ruta = juego.getImgPath();

        Resource file = storageService.loadImg(ruta);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

}
