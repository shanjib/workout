package com.shanjib.workout.dto;

import java.util.List;

public record GetLatestWorkoutsResponseDTO (
        List<GetLatestWorkoutDTO> latestWorkouts
) {}
