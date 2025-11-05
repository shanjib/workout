package com.example.demo.repository;

import com.example.demo.model.Workout;
import com.example.demo.model.WorkoutType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findTopByOrderByDateDesc();
    Optional<Workout> findTopByTypeOrderByDateDesc(WorkoutType type);
    List<Workout> findByType(WorkoutType type);
}