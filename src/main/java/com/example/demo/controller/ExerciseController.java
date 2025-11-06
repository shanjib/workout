package com.example.demo.controller;

import com.example.demo.dto.ExerciseRequestDTO;
import com.example.demo.dto.ExerciseUpdateDTO;
import com.example.demo.model.Exercise;
import com.example.demo.model.TrackedExercise;
import com.example.demo.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Constants.EXERCISES)
@RequiredArgsConstructor
public class ExerciseController {
    private final DatabaseService service;

    @PostMapping(Constants.NEW)
    public ResponseEntity<List<Exercise>> addExercise(@RequestBody List<ExerciseRequestDTO> exerciseDTOs) {
        List<Exercise> saved = new ArrayList<>();
        for (ExerciseRequestDTO exerciseDTO : exerciseDTOs) {
            Exercise exercise = Exercise.builder()
                    .name(exerciseDTO.getName())
                    .type(exerciseDTO.getType())
                    .sets(exerciseDTO.getSets())
                    .reps(exerciseDTO.getReps())
                    .weightIncrement(exerciseDTO.getWeightIncrement())
                    .initialWeight(exerciseDTO.getInitialWeight())
                    .build();
            Exercise savedExercise = service.saveExercise(exercise);
            saved.add(savedExercise);
        }
        return ResponseEntity.ok(saved);
    }

    @PostMapping(Constants.TRACKED_EXERCISES + Constants.UPDATE)
    public ResponseEntity<TrackedExercise> updateTrackedExercise(@RequestBody ExerciseUpdateDTO exerciseDTO) {
       TrackedExercise trackedExercise = service.getTrackedExerciseById(exerciseDTO.getId());
       if (trackedExercise == null) {
           return ResponseEntity.notFound().build();
       } else {
           trackedExercise.getRepsPerSet().put(exerciseDTO.getSetNumber(), exerciseDTO.getRepsCompleted());
           trackedExercise = service.saveTrackedExercise(trackedExercise);
           return ResponseEntity.ok(trackedExercise);
       }
    }
}
