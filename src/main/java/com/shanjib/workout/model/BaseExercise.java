package com.shanjib.workout.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class BaseExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int sets;
    private int reps;
    private double weightIncrement;
    private double initialWeight;

    @Enumerated(EnumType.STRING)
    private WorkoutType type;
}