package com.shanjib.workout.dto;

import java.util.Map;

public record GetNextWorkoutDetailsResponseDTO(
        String type,
        Map<String, Double> exerciseToWeight
) {}
