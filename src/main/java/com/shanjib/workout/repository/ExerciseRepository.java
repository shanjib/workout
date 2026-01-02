package com.shanjib.workout.repository;

import com.shanjib.workout.model.Exercise;
import com.shanjib.workout.model.WorkoutType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByType(WorkoutType type);
    Exercise findByName(String name);
}