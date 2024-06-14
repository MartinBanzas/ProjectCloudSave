package com.martin.springboot.cloudsave.controller;

import com.martin.springboot.cloudsave.db_entities.Img;
import com.martin.springboot.cloudsave.db_entities.Juego;
import com.martin.springboot.cloudsave.db_entities.Partida;
import com.martin.springboot.cloudsave.response.ResponseMessage;
import com.martin.springboot.cloudsave.service.FileStorageService;
import com.martin.springboot.cloudsave.service.ImgService;
import com.martin.springboot.cloudsave.service.JuegoService;
import com.martin.springboot.cloudsave.service.PartidaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
public class FileController {

    private final Path root = Paths.get("./uploads");

    @Autowired
    private ImgService imgService;

    @Autowired
    private  FileStorageService storageService;

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private PartidaService partidaService;


    @GetMapping("/files/new")
    public String newFile(Model model) {
        return "juegos/uploadSave";
    }

    //Subir partidas
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


    //Borrar partidas
    @PostMapping("/files/delete/{id}")
    @Transactional
    public String deleteFile(@PathVariable int id) {

        Partida partida = partidaService.findById(id);
        Resource file = storageService.load(partida.getRutaarchivo());
        storageService.delete(file.getFilename());
        partidaService.deleteById(id);

return "redirect:/juegos/lista";

    }

    //Descarga de partidas
        @GetMapping("/files/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable int id) {
        Partida partida = partidaService.findById(id);
        Resource file = storageService.load(partida.getRutaarchivo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    //Subida de carátulas
    @PostMapping("/img/uploadImg")
    public ResponseEntity<ResponseMessage> uploadImg(@RequestParam("id") int id, @RequestParam("img") MultipartFile file) {
        String message;
        Img img = new Img(file.getOriginalFilename(), file.getContentType());
      Juego theGame = juegoService.findById(id);
      img.setJuego(theGame);
        imgService.save(img);

        try {
            storageService.saveImg(file);
            message = "File uploaded successfully: " + img.getName();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + img.getName() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    //Obtener las carátulas de la página principal
    @GetMapping("/img/{id}")
    public ResponseEntity<Resource> getImg(@PathVariable int id) {

        Juego juego = juegoService.findById(id);
        String ruta = juego.getImg().getName();

        Resource file = storageService.loadImg(ruta);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
    }

}
