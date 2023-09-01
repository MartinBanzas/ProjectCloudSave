package com.luv2code.springboot.thymeleafdemo.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements  FileStorageService{

    private final Path imgDir = Paths.get("./img");

    private final Path root = Paths.get("./uploads");
    @Override
    public void init() {

        try {
            Files.createDirectories(root);
            Files.createDirectories(imgDir);
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido inicializar el directorio");
        }
    }

    @Override
    public void save(MultipartFile file, String uniqueFileName) {

        try {

            Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName));
        } catch (Exception e) {
           if (e instanceof FileAlreadyExistsException) {
               throw new RuntimeException("Ya hay archivo con ese nombre");
           }
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void saveImg(MultipartFile file) {

        try {

            Files.copy(file.getInputStream(), this.imgDir.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("Ya hay archivo con ese nombre");
            }
            throw new RuntimeException(e.getMessage());
        }

    }


    @Override
    public Resource load(String filename) {
      try {
          Path file = root.resolve(filename);
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
    public Resource loadImg(String filename) {
        try {
            Path file = this.imgDir.resolve(filename);
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
    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public boolean deleteImg(String filename) {
        try {
            Path file = this.imgDir.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }



    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
