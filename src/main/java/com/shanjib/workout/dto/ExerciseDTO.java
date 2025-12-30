package com.shanjib.workout.dto;

import jakarta.validation.constraints.NotNull;

public record ExerciseDTO (
        @NotNull String name,
        @NotNull String type,
        @NotNull int sets,
        @NotNull int reps,
        @NotNull double weightIncrement,
        @NotNull double initialWeight
) {}
