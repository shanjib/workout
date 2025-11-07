package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.DatabaseService;
import com.example.demo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.WORKOUTS)
@RequiredArgsConstructor
public class WorkoutController {
    private final DatabaseService service;
    private final DateUtil dateUtil;

    @GetMapping(Constants.GET)
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(service.getAllWorkouts());
    }

    @GetMapping(Constants.GET + "/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable long id) {
        Workout workout = service.getWorkoutById(id);
        if (workout == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(workout);
        }
    }

    @GetMapping(Constants.NEW)
    public Workout getNewWorkout() {
        Workout lastWorkout = service.getLastWorkout();
        WorkoutType type = lastWorkout == null ? WorkoutType.PUSH : getNextWorkoutType(lastWorkout.getType());
        Workout newWorkout = buildNewWorkout(type);
        return service.saveWorkout(newWorkout);
    }

    private WorkoutType getNextWorkoutType(WorkoutType currentType) {
        return switch (currentType) {
            case PUSH -> WorkoutType.PULL;
            case PULL -> WorkoutType.LEG;
            case LEG -> WorkoutType.PUSH;
            default -> currentType;
        };
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
                        .repsPerSet(new HashMap<>())
                        .build())
                .collect(Collectors.toList());
        Workout lastOfType = service.getLastWorkoutOfType(type);
        if (lastOfType != null) {
            updateWeights(trackedExercises, lastOfType.getTrackedExercises());
        }
        newWorkout.setTrackedExercises(trackedExercises);
        return newWorkout;
    }

    private void updateWeights(List<TrackedExercise> current, List<TrackedExercise> last) {
        Map<String, TrackedExercise> currentByName = current.stream().collect(Collectors.toMap(TrackedExercise::getName, Function.identity()));
        Map<String, TrackedExercise> lastByName = last.stream().collect(Collectors.toMap(TrackedExercise::getName, Function.identity()));
        currentByName.forEach((name, te) -> {
            if (lastByName.containsKey(name)) {
                TrackedExercise lte = lastByName.get(name);
                if (lte.isSuccessful()) {
                    te.setWeight(lte.getWeight() + te.getWeightIncrement());
                }
            }
        });

    }
}