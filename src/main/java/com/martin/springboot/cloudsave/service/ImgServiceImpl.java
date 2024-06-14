package com.martin.springboot.cloudsave.service;

import com.martin.springboot.cloudsave.dao.ImgRepository;
import com.martin.springboot.cloudsave.db_entities.Img;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class ImgServiceImpl implements ImgService {


    @Autowired
    private ImgRepository imgRepository;

    @Override
    public void save(Img img) {
     imgRepository.save(img);
    }



}
