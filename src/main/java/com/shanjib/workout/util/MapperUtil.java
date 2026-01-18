package com.shanjib.workout.util;

import com.shanjib.workout.dto.ExerciseDTO;
import com.shanjib.workout.dto.GetLatestWorkoutDTO;
import com.shanjib.workout.dto.WorkoutDTO;
import com.shanjib.workout.dto.WorkoutExerciseDTO;
import com.shanjib.workout.model.Exercise;
import com.shanjib.workout.model.TrackedExercise;
import com.shanjib.workout.model.Workout;
import com.shanjib.workout.model.WorkoutType;

import java.util.ArrayList;
import java.util.List;

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

    public static WorkoutDTO map(Workout workout) {
        List<WorkoutExerciseDTO> workoutExerciseDTOS = new ArrayList<>();
        for (TrackedExercise trackedExercise : workout.getTrackedExercises()) {
            workoutExerciseDTOS.add(map(trackedExercise));
        }
        return new WorkoutDTO(
                workout.getType().name(),
                workout.getDate(),
                workoutExerciseDTOS
        );
    }

    public static WorkoutExerciseDTO map(TrackedExercise trackedExercise) {
        return new WorkoutExerciseDTO(
                trackedExercise.getId(),
                trackedExercise.getName(),
                trackedExercise.getType().name(),
                trackedExercise.getWeight(),
                trackedExercise.getInitialWeight(),
                trackedExercise.getInitialWeight() != 0,
                trackedExercise.getReps(),
                trackedExercise.getRepsPerSet(),
                trackedExercise.getNotes()
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
