# Workout Service

This application is meant to track a push/pull/leg split, with the occasional cardio workout.

## Endpoints

| METHOD | ENDPOINT            | DESCRIPTION                 |
|--------|---------------------|-----------------------------|
| GET    | /api/exercises      | get all exercises           |
| GET    | /api/exercises/{id} | get 1 exercise              |
| POST   | /api/exercises      | create an exercise          |
| PUT    | /api/exercises/{id} | update 1 exercise           |
|        |                     |
| GET    | /api/workouts       | get all workouts            |
| GET    | /api/workouts/{id}  | get 1 workout               |
| GET    | /api/workouts/next  | get details of next workout |
| POST   | /api/workouts       | create an workout           |
| PUT    | /api/workouts/{id}  | update 1 workout            |
