package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseUpdateDTO {
    private long id;
    private int setNumber;
    private int repsCompleted;
}
