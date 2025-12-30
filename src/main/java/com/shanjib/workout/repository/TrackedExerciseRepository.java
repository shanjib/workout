package com.shanjib.workout.repository;

import com.shanjib.workout.model.TrackedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackedExerciseRepository extends JpaRepository<TrackedExercise, Long> {}
