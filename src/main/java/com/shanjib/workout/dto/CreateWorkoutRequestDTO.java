package com.shanjib.workout.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record CreateWorkoutRequestDTO(
        @NotNull String type,
        @NotEmpty @Min(1) Map<String, Double> exerciseToWeight
) {
}
