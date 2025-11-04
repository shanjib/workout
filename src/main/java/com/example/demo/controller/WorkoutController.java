package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.WORKOUTS)
public class WorkoutController {
    private final WorkoutService service;

    public WorkoutController(WorkoutService service) {
        this.service = service;
    }

    @GetMapping(Constants.NEW)
    public Workout getNewWorkout() {
        Optional<Workout> lastWorkoutOpt = service.getLastWorkout();
        Workout newWorkout = new Workout();
        newWorkout.setDate(LocalDate.now());

        if (lastWorkoutOpt.isPresent()) {
            Workout lastWorkout = lastWorkoutOpt.get();
            newWorkout.setType(service.getNextWorkoutType(lastWorkout.getType()));
        } else {
            newWorkout.setType(WorkoutType.PUSH);
        }
        List<Exercise> exercises = service.getExercisesByType(newWorkout.getType());

        List<TrackedExercise> trackedExercises = exercises.stream()
                .map(e -> {
                    TrackedExercise te = new TrackedExercise();
                    te.setName(e.getName());
                    te.setSets(e.getSets());
                    te.setReps(e.getReps());
                    te.setWeight(e.getInitialWeight());
                    te.setType(e.getType());
                    te.setWorkout(newWorkout);
                    te.setRepsPerSet(new ArrayList<>());
                    return te;
                })
                .collect(Collectors.toList());

        newWorkout.setTrackedExercises(trackedExercises);
        return service.saveWorkout(newWorkout);
    }
}