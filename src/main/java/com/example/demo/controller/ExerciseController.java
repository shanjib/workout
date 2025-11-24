package com.example.demo.controller;

import com.example.demo.model.Exercise;
import com.example.demo.model.TrackedExercise;
import com.example.demo.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Constants.API + Constants.EXERCISES)
@RequiredArgsConstructor
@Slf4j
public class ExerciseController {
    private final DatabaseService service;

    @PostMapping(Constants.NEW)
    public ResponseEntity<List<Exercise>> addExercise(@RequestBody List<Exercise> exercises) {
        List<Exercise> saved = new ArrayList<>();
        for (Exercise exercise : exercises) {
            Exercise savedExercise = service.saveExercise(exercise);
            saved.add(savedExercise);
        }
        return ResponseEntity.ok(saved);
    }

    @PostMapping(Constants.TRACKED_EXERCISES + Constants.UPDATE + Constants.ID)
    public ResponseEntity<TrackedExercise> updateTrackedExercise(
            @PathVariable long id,
            @RequestParam int set,
            @RequestParam(required = false) Boolean successfulRep,
            @RequestParam(required = false) Boolean successfulSet
    ) {
        log.info("Updating exercise {}, set {}, with value {}, {}", id, set, successfulRep, successfulSet);
        TrackedExercise trackedExercise = service.getTrackedExerciseById(id);
        if (trackedExercise == null) {
            return ResponseEntity.notFound().build();
        }
        trackedExercise.setupData(set);
        if (successfulRep != null) {
            trackedExercise.updateRep(set, successfulRep);
        } else if (successfulSet != null) {
            trackedExercise.updateSet(set, successfulSet);
        } else {
            return ResponseEntity.badRequest().build();
        }
        TrackedExercise updatedTrackedExercise = service.saveTrackedExercise(trackedExercise);
        return ResponseEntity.ok(updatedTrackedExercise);
    }
}
