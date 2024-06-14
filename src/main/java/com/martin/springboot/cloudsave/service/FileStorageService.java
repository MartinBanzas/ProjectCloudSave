package com.martin.springboot.cloudsave.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;



public interface FileStorageService {

    public void init();

    public void save(MultipartFile file, String uniqueFileName);

    public void saveImg(MultipartFile file);

    public Resource load(String filename);

    public Resource loadImg(String filename);

    public boolean delete(String filename);

    public boolean deleteImg(String filename);

    public void deleteAll();

    public Stream<Path> loadAll();



}
