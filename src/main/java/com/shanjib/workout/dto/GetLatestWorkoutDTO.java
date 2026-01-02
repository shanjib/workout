package com.shanjib.workout.dto;

import java.time.LocalDate;

public record GetLatestWorkoutDTO(
        LocalDate date,
        String type,
        Long id
) {}
