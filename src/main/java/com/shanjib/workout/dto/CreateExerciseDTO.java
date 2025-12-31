package com.shanjib.workout.dto;

public record CreateExerciseDTO(
        String name,
        boolean success,
        long id
) {
}
