package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Img;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface ImgService {

    public Img store(int id, MultipartFile img) throws IOException;

    public Img getImg(int id);

    public Stream<Img> getAllImg();

    public Resource load(String filename);
    public Img findById(int id);

}
