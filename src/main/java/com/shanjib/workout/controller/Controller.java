package com.shanjib.workout.controller;

import com.shanjib.workout.dto.*;
import com.shanjib.workout.model.Exercise;
import com.shanjib.workout.model.TrackedExercise;
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
import org.springframework.data.domain.Limit;
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
        log.info("Creating workout with request: {}", requestDTO);
        Workout workout = Workout.builder()
                .type(MapperUtil.map(requestDTO.type()))
                .date(requestDTO.date())
                .build();
        List<TrackedExercise> trackedExercises = new ArrayList<>();
        for (String exerciseName : requestDTO.exerciseToWeight().keySet()) {
            Exercise exercise = exerciseRepository.findByName(exerciseName);
            TrackedExercise trackedExercise = TrackedExercise.builder()
                    .name(exercise.getName())
                    .sets(exercise.getSets())
                    .reps(exercise.getReps())
                    .type(exercise.getType())
                    .initialWeight(exercise.getInitialWeight())
                    .weight(requestDTO.exerciseToWeight().get(exerciseName))
                    .workout(workout)
                    .build();
            trackedExercise.initRepsPerSet();
            trackedExercises.add(trackedExercise);
        }
        workout.setTrackedExercises(trackedExercises);
        Workout savedWorkout = workoutRepository.save(workout);
        return ResponseEntity.ok(new CreateWorkoutResponseDTO(savedWorkout.getId()));
    }

    @GetMapping(Constants.WORKOUTS)
    public ResponseEntity<GetLatestWorkoutsResponseDTO> getLatestWorkouts() {
        log.info("Getting latest workouts");
        List<Workout> workouts = workoutRepository.findAllByOrderByDateDesc(Limit.of(12));
        List<GetLatestWorkoutDTO> latestWorkoutDTOS = new ArrayList<>();
        for (Workout workout : workouts) {
            GetLatestWorkoutDTO latestWorkoutDTO = new GetLatestWorkoutDTO(
                    workout.getDate(),
                    workout.getType().name(),
                    workout.getId()
            );
            latestWorkoutDTOS.add(latestWorkoutDTO);
        }
        log.info("Returning {} workouts", latestWorkoutDTOS.size());
        return ResponseEntity.ok(new GetLatestWorkoutsResponseDTO(latestWorkoutDTOS));
    }

    @GetMapping(Constants.WORKOUTS + Constants.NEXT)
    public ResponseEntity<GetNextWorkoutDetailsResponseDTO> getNextWorkoutDetails() {
        log.info("Getting next workout details");
        Workout lastWorkout = workoutRepository.findTopByOrderByDateDesc().orElse(null);
        WorkoutType type = lastWorkout == null ? WorkoutType.PUSH : MapperUtil.getNextWorkoutType(lastWorkout.getType());
        Map<String, Double> nameToWeight = new HashMap<>();
        Map<String, String> nameToNotes = new HashMap<>();
        Optional<Workout> lastWorkoutOfType = workoutRepository.findTopByTypeOrderByDateDesc(type);
        if (lastWorkoutOfType.isPresent()) {
            for (TrackedExercise trackedExercise : lastWorkoutOfType.get().getTrackedExercises()) {
                nameToWeight.put(trackedExercise.getName(), trackedExercise.getWeight());
                nameToNotes.put(trackedExercise.getName(), trackedExercise.getNotes());
            }
        } else {
            List<Exercise> byType = exerciseRepository.findByType(type);
            for (Exercise exercise : byType) {
                nameToWeight.put(exercise.getName(), exercise.getInitialWeight());
                nameToNotes.put(exercise.getName(), null);
            }
        }
        GetNextWorkoutDetailsResponseDTO responseDTO = new GetNextWorkoutDetailsResponseDTO(
                type.name(), dateUtil.getCurrentDate(), nameToWeight, nameToNotes);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(Constants.WORKOUTS + Constants.ID)
    public ResponseEntity<GetWorkoutResponseDTO> getWorkout(
            @PathVariable Long id) {
        log.info("Getting workout {}", id);
        Optional<Workout> workout = workoutRepository.findById(id);
        if (workout.isPresent()) {
            WorkoutDTO workoutDTO = MapperUtil.map(workout.get());
            return ResponseEntity.ok(new GetWorkoutResponseDTO(workoutDTO));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(Constants.WORKOUTS + Constants.ID)
    public ResponseEntity<UpdateWorkoutResponseDTO> updateWorkout(
            @PathVariable Long id,
            @Valid @RequestBody UpdateWorkoutRequestDTO requestDTO) {
        log.info("Updating workout {} with {}", id, requestDTO);
        Optional<Workout> optionalWorkout = workoutRepository.findById(id);
        if (optionalWorkout.isPresent()) {
            Workout workout = optionalWorkout.get();
            WorkoutDTO workoutDTO = requestDTO.workout();

            workout.setDate(workoutDTO.date());
            workout.setType(MapperUtil.map(workoutDTO.type()));

            for (WorkoutExerciseDTO exerciseDTO : workoutDTO.exercises()) {
                for (TrackedExercise exercise : workout.getTrackedExercises()) {
                    if (exercise.getId() == exerciseDTO.id()) {
                        exercise.setWeight(exerciseDTO.weight());
                        exercise.setRepsPerSet(exerciseDTO.setsToReps());
                        exercise.setNotes(exerciseDTO.notes());
                    }
                }
            }
            workoutRepository.save(workout);
            return ResponseEntity.ok(new UpdateWorkoutResponseDTO(true));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(Constants.WORKOUTS + Constants.ID)
    public void deleteWorkout(@PathVariable Long id) {
        log.info("Deleting workout {}", id);
        workoutRepository.deleteById(id);
    }

    @GetMapping(Constants.EXERCISES)
    public ResponseEntity<GetExercisesResponseDTO> getExercises() {
        log.info("Getting all exercises");
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
        log.info("Getting exercise {}", id);
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
        log.info("Creating exercises {}", requestDTO);
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
        log.info("Updating exerices {} with {}", id, requestDTO);
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
