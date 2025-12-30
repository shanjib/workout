package com.shanjib.workout.dto;

import java.util.List;

public record GetExercisesResponseDTO(
        List<ExerciseDTO> exercises
) {
}
