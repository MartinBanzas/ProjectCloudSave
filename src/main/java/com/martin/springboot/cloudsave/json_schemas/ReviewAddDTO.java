package com.martin.springboot.cloudsave.json_schemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAddDTO {


    private int gameId;
    private int rate;
    private String reviewName;
    private String reviewComments;
    
}
