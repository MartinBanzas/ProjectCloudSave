package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.dao.ImgRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ImgServiceImpl implements ImgService {


    @Autowired
    private ImgRepository imgRepository;

    @Override
    public void save(Img img) {
     imgRepository.save(img);
    }



}
