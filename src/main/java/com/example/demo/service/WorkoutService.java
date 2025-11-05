package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public Optional<Workout> getLastWorkout() {
        return workoutRepository.findTopByOrderByDateDesc();
    }

    public Optional<Workout> getLastWorkoutOfType(WorkoutType type) {
        return workoutRepository.findTopByTypeOrderByDateDesc(type);
    }

    public WorkoutType getNextWorkoutType(WorkoutType currentType) {
        return switch (currentType) {
            case PUSH -> WorkoutType.PULL;
            case PULL -> WorkoutType.LEG;
            case LEG -> WorkoutType.PUSH;
            default -> currentType;
        };
    }

    public List<Exercise> getExercisesByType(WorkoutType type) {
        return exerciseRepository.findByType(type);
    }
}