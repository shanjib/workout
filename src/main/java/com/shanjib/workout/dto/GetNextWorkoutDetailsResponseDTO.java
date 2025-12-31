package com.shanjib.workout.dto;

import java.time.LocalDate;
import java.util.Map;

public record GetNextWorkoutDetailsResponseDTO(
        String type,
        LocalDate date,
        Map<String, Double> exerciseToWeight
) {}
