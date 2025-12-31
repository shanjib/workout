package com.shanjib.workout.dto;

import java.util.List;

public record CreateExerciseResponseDTO(
        List<CreateExerciseDTO> exercises
) {
}
