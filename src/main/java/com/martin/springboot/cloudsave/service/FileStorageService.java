package com.martin.springboot.cloudsave.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;



public interface FileStorageService {

     void init();
     void save(MultipartFile file, String uniqueFileName);
     void saveImg(MultipartFile file);
     Resource load(String filename);
     Resource loadImg(String filename);
     boolean delete(String filename);
     boolean deleteImg(String filename);
     void deleteAll();
     Stream<Path> loadAll();



}
