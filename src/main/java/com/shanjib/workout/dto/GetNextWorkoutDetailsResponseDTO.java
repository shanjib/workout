package com.shanjib.workout.dto;

import java.time.LocalDate;
import java.util.List;

public record GetNextWorkoutDetailsResponseDTO(
        String type,
        LocalDate date,
        List<WorkoutExerciseDTO> exercises
) {}
