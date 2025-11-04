package com.example.demo.dto;

import com.example.demo.model.WorkoutType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRequestDTO {
    private String name;
    private WorkoutType type;
    private int sets;
    private int reps;
    private double weightIncrement;
    private double initialWeight;
}
