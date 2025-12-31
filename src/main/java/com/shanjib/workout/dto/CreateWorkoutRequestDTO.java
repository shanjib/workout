package com.shanjib.workout.dto;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Map;

public record CreateWorkoutRequestDTO(
        @NotNull String type,
        @NotNull LocalDate date,
        @NotEmpty Map<String, Double> exerciseToWeight
) {
}
