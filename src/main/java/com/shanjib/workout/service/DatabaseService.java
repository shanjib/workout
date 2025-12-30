package com.shanjib.workout.service;

import com.shanjib.workout.model.Exercise;
import com.shanjib.workout.model.TrackedExercise;
import com.shanjib.workout.model.Workout;
import com.shanjib.workout.model.WorkoutType;
import com.shanjib.workout.repository.ExerciseRepository;
import com.shanjib.workout.repository.TrackedExerciseRepository;
import com.shanjib.workout.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DatabaseService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrackedExerciseRepository trackedExerciseRepository;

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout getWorkoutById(long id) {
        return workoutRepository.findById(id).orElse(null);
    }

    public Workout saveWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public Workout getLastWorkout() {
        return workoutRepository.findTopByOrderByDateDesc().orElse(null);
    }

    public Workout getLastWorkoutOfType(WorkoutType type) {
        return workoutRepository.findTopByTypeOrderByDateDesc(type).orElse(null);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getExercisesByType(WorkoutType type) {
        return exerciseRepository.findByType(type);
    }

    public TrackedExercise saveTrackedExercise(TrackedExercise exercise) {
        return trackedExerciseRepository.save(exercise);
    }

    public TrackedExercise getTrackedExerciseById(long id) {
        return trackedExerciseRepository.findById(id).orElse(null);
    }
}