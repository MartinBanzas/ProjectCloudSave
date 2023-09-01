package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Img;
import org.springframework.core.io.Resource;

import java.util.stream.Stream;

public interface ImgService {

    public void save(Img img);

}
