package com.martin.springboot.cloudsave.dao;

import com.martin.springboot.cloudsave.db_entities.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<Img, Integer> {

}
