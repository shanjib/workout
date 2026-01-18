package com.shanjib.workout.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record WorkoutExerciseDTO(
        long id,
        @NotNull String name,
        @NotNull String type,
        @NotNull Double weight,
        @NotNull Double initialWeight,
        @NotNull Boolean barExercise,
        @NotNull Integer reps,
        @NotNull @NotEmpty Map<Integer, Integer> setsToReps,
        String notes
) {}
