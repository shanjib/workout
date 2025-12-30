package com.shanjib.workout.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record WorkoutDTO(
        @NotNull String type,
        @NotNull LocalDate date,
        @NotNull @NotEmpty List<WorkoutExerciseDTO> exercises
) {}
