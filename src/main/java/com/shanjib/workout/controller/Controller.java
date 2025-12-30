package com.shanjib.workout.controller;

import com.shanjib.workout.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API)
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @PostMapping(Constants.WORKOUTS)
    public ResponseEntity<CreateWorkoutResponseDTO> createWorkout(
            @Valid @RequestBody CreateWorkoutRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(Constants.WORKOUTS)
    public ResponseEntity<GetLatestWorkoutsResponseDTO> getLatestWorkouts() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(Constants.WORKOUTS + Constants.NEXT)
    public ResponseEntity<GetNextWorkoutDetailsResponseDTO> getNextWorkoutDetails() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(Constants.WORKOUTS + Constants.ID)
    public ResponseEntity<GetWorkoutResponseDTO> getWorkout(
            @PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(Constants.WORKOUTS + Constants.ID)
    public ResponseEntity<UpdateWorkoutResponseDTO> updateWorkout(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWorkoutRequestDTO requestDTO) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(Constants.EXERCISES)
    public ResponseEntity<GetExerciseResponseDTO> getExercises() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(Constants.EXERCISES + Constants.ID)
    public ResponseEntity<GetExercisesResponseDTO> getExercise(
            @PathVariable Long id) {
        return ResponseEntity.ok().build();
    }

    @PostMapping(Constants.EXERCISES)
    public ResponseEntity<CreateExerciseResponseDTO> createExercise(
            @Valid @RequestBody CreateExerciseRequestDTO requestDTO) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(Constants.EXERCISES + Constants.ID)
    public ResponseEntity<UpdateExerciseResponseDTO> updateExercise(
            @PathVariable Long id,
            @Valid @RequestBody UpdateExerciseRequestDTO requestDTO) {
        return ResponseEntity.ok().build();
    }
}
