package com.shanjib.workout.dto;

import java.time.LocalDate;

public record GetLatestWorkoutsResponseDTO (
        LocalDate date,
        String type,
        Long id
) {}
