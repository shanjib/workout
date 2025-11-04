package com.example.demo.controller;

import com.example.demo.dto.ExerciseRequestDTO;
import com.example.demo.model.Exercise;
import com.example.demo.repository.ExerciseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Constants.EXERCISES)
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @PostMapping(Constants.NEW)
    public ResponseEntity<List<Exercise>> addExercise(@RequestBody List<ExerciseRequestDTO> exerciseDTOs) {
        List<Exercise> saved = new ArrayList<>();
        for (ExerciseRequestDTO exerciseDTO : exerciseDTOs) {
            Exercise exercise = new Exercise();
            exercise.setName(exerciseDTO.getName());
            exercise.setType(exerciseDTO.getType());
            exercise.setSets(exerciseDTO.getSets());
            exercise.setReps(exerciseDTO.getReps());
            exercise.setWeightIncrement(exerciseDTO.getWeightIncrement());
            exercise.setInitialWeight(exerciseDTO.getInitialWeight());
            Exercise savedExercise = exerciseRepository.save(exercise);
            saved.add(savedExercise);
        }
        return ResponseEntity.ok(saved);
    }
}
