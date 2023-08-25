package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Img;
import com.luv2code.springboot.thymeleafdemo.response.ResponseFile;
import com.luv2code.springboot.thymeleafdemo.response.ResponseMessage;
import com.luv2code.springboot.thymeleafdemo.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/juegos")
public class ImgController {

    @Autowired
    private ImgService imgService;

    @PostMapping("/files/uploadImg")
    @CrossOrigin("http://localhost:8080")
    public ResponseEntity<ResponseMessage> uploadImg(@RequestParam("id") int id,@RequestParam("img")MultipartFile img) {
        String message="";
        try {
            imgService.store(id,img);
            message = "Uploaded the file successfully: " + img.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + img.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files/img")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = imgService.getAllImg().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/img")
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/img/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable int id) {
        Img img = imgService.findById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + img.getName() + "\"")
                .body(img.getData());
    }
}
