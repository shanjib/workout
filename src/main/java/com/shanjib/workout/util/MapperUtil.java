package com.shanjib.workout.util;

import com.shanjib.workout.dto.ExerciseDTO;
import com.shanjib.workout.model.Exercise;
import com.shanjib.workout.model.WorkoutType;

public class MapperUtil {
    public static Exercise map(ExerciseDTO exerciseDTO) {
        return Exercise.builder()
                .name(exerciseDTO.name())
                .sets(exerciseDTO.sets())
                .reps(exerciseDTO.reps())
                .weightIncrement(exerciseDTO.weightIncrement())
                .initialWeight(exerciseDTO.initialWeight())
                .type(map(exerciseDTO.type()))
                .build();
    }

    public static ExerciseDTO map(Exercise exercise) {
        return new ExerciseDTO(
                exercise.getId(),
                exercise.getName(),
                exercise.getType().name(),
                exercise.getSets(),
                exercise.getReps(),
                exercise.getWeightIncrement(),
                exercise.getInitialWeight()
        );
    }

    public static WorkoutType map(String type) {
        return switch (type) {
            case "PUSH" -> WorkoutType.PUSH;
            case "PULL" -> WorkoutType.PULL;
            case "LEG" -> WorkoutType.LEG;
            default -> WorkoutType.CARDIO;
        };
    }

    public static WorkoutType getNextWorkoutType(WorkoutType currentType) {
        return switch (currentType) {
            case PUSH -> WorkoutType.PULL;
            case PULL -> WorkoutType.LEG;
            case LEG -> WorkoutType.PUSH;
            default -> currentType;
        };
    }
}
