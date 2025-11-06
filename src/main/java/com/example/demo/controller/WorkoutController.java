package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.DatabaseService;
import com.example.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.WORKOUTS)
@RequiredArgsConstructor
public class WorkoutController {
    private final DatabaseService service;
    private final DateUtil dateUtil;

    @GetMapping(Constants.NEW)
    public Workout getNewWorkout() {
        Optional<Workout> lastWorkoutOpt = service.getLastWorkout();
        WorkoutType type = WorkoutType.PUSH;
        if (lastWorkoutOpt.isPresent()) {
            Workout lastWorkout = lastWorkoutOpt.get();
            type = service.getNextWorkoutType(lastWorkout.getType());
        }

        Workout newWorkout = buildNewWorkout(type);
        return service.saveWorkout(newWorkout);
    }

    private Workout buildNewWorkout(WorkoutType type) {
        Workout newWorkout = new Workout();
        newWorkout.setDate(dateUtil.getCurrentDate());
        newWorkout.setType(type);
        List<Exercise> exercises = service.getExercisesByType(type);

        List<TrackedExercise> trackedExercises = exercises.stream()
                .map(e -> TrackedExercise.builder()
                        .name(e.getName())
                        .sets(e.getSets())
                        .reps(e.getReps())
                        .initialWeight(e.getInitialWeight())
                        .weightIncrement(e.getWeightIncrement())
                        .weight(e.getInitialWeight())
                        .type(e.getType())
                        .workout(newWorkout)
                        .repsPerSet(new ArrayList<>())
                        .build())
                .collect(Collectors.toList());

        newWorkout.setTrackedExercises(trackedExercises);
        return newWorkout;
    }
}