package com.shanjib.workout.repository;

import com.shanjib.workout.model.Workout;
import com.shanjib.workout.model.WorkoutType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findTopByOrderByDateDesc();
    Optional<Workout> findTopByTypeOrderByDateDesc(WorkoutType type);
    List<Workout> findByType(WorkoutType type);
}