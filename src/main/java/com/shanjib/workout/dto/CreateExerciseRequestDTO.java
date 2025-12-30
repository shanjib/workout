package com.shanjib.workout.dto;

import java.util.List;

public record CreateExerciseRequestDTO(
        List<ExerciseDTO> exercises
) {
}
