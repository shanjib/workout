# Workout Service

This application is meant to track a push/pull/leg split, with the occasional cardio workout.

## Endpoints

### Exercise
/api/exercise/new - create a new exercise
/api/exercise/tracked/update/{id} - update an existing workout

### Workout
/api/workout/get - get all
/api/workout/get/{id} - get specific
/api/workout/new - get new workout based on push/pull/leg and prior workout