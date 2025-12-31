package com.shanjib.workout.controller;

import com.shanjib.workout.dto.*;
import com.shanjib.workout.model.Exercise;
import com.shanjib.workout.model.Workout;
import com.shanjib.workout.model.WorkoutType;
import com.shanjib.workout.repository.ExerciseRepository;
import com.shanjib.workout.repository.TrackedExerciseRepository;
import com.shanjib.workout.repository.WorkoutRepository;
import com.shanjib.workout.util.DateUtil;
import com.shanjib.workout.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(Constants.API)
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrackedExerciseRepository trackedExerciseRepository;
    private final DateUtil dateUtil;

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
        Workout lastWorkout = workoutRepository.findTopByOrderByDateDesc().orElse(null);
        WorkoutType type = lastWorkout == null? WorkoutType.PUSH : MapperUtil.getNextWorkoutType(lastWorkout.getType());
        Map<String, Double> nameToWeight = new HashMap<>();

        List<Exercise> byType = exerciseRepository.findByType(type);
        for (Exercise exercise : byType) {
            nameToWeight.put(exercise.getName(), exercise.getInitialWeight());
        }
        GetNextWorkoutDetailsResponseDTO responseDTO = new GetNextWorkoutDetailsResponseDTO(
                type.name(), dateUtil.getCurrentDate(), nameToWeight);
        return ResponseEntity.ok(responseDTO);
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
    public ResponseEntity<GetExercisesResponseDTO> getExercises() {
        List<ExerciseDTO> getResponses = new ArrayList<>();
        for (Exercise exercise : exerciseRepository.findAll()) {
            ExerciseDTO exerciseDTO = MapperUtil.map(exercise);
            getResponses.add(exerciseDTO);
        }
        return ResponseEntity.ok(new GetExercisesResponseDTO(getResponses));
    }

    @GetMapping(Constants.EXERCISES + Constants.ID)
    public ResponseEntity<GetExerciseResponseDTO> getExercise(
            @PathVariable Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        if (exercise.isPresent()) {
            ExerciseDTO exerciseDTO = MapperUtil.map(exercise.get());
            return ResponseEntity.ok(new GetExerciseResponseDTO(exerciseDTO));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(Constants.EXERCISES)
    public ResponseEntity<CreateExerciseResponseDTO> createExercise(
            @Valid @RequestBody CreateExerciseRequestDTO requestDTO) {
        List<CreateExerciseDTO> createResponses = new ArrayList<>();
        for (ExerciseDTO exerciseDTO : requestDTO.exercises()) {
            Exercise exercise = MapperUtil.map(exerciseDTO);
            Exercise savedExercise = exerciseRepository.save(exercise);
            createResponses.add(new CreateExerciseDTO(savedExercise.getName(), true, savedExercise.getId()));
        }
        return ResponseEntity.ok(new CreateExerciseResponseDTO(createResponses));
    }

    @PutMapping(Constants.EXERCISES + Constants.ID)
    public ResponseEntity<UpdateExerciseResponseDTO> updateExercise(
            @PathVariable Long id,
            @Valid @RequestBody UpdateExerciseRequestDTO requestDTO) {
        try {
            Exercise exercise = MapperUtil.map(requestDTO.exercise());
            exercise.setId(id);
            Exercise savedExercise = exerciseRepository.save(exercise);
            return ResponseEntity.ok(new UpdateExerciseResponseDTO(true));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
