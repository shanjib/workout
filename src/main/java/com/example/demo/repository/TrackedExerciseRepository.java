package com.example.demo.repository;

import com.example.demo.model.TrackedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackedExerciseRepository extends JpaRepository<TrackedExercise, Long> {}
