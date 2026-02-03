package com.shanjib.workout.dto;

import java.util.Map;

public record UpdateWorkoutRequestDTO(
        WorkoutDTO workout,
        Map<String, Double> newExerciseToWeight
) {
}
