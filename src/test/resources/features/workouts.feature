Feature: Workout Management

  Scenario: Create a new workout
    Given the application is running
    When I create new exercises with:
    """
[
  {
    "name": "Bench Press",
    "type": "PUSH",
    "sets": 5,
    "reps": 5,
    "weightIncrement": 5.0,
    "initialWeight": 45
  },
  {
    "name": "Deadlift",
    "type": "PULL",
    "sets": 5,
    "reps": 5,
    "weightIncrement": 5.0,
    "initialWeight": 45
  },
  {
    "name": "Squat",
    "type": "LEG",
    "sets": 5,
    "reps": 5,
    "weightIncrement": 5.0,
    "initialWeight": 45
  }
]
    """
    Then the exercises are saved successfully
    When I request a new workout
    Then the response should be
    """
  {
    "id": 1,
    "date": "2025-01-02",
    "type": "PUSH",
    "trackedExercises": [
      {
        "id": 1,
        "name": "Bench Press",
        "type": "PUSH",
        "sets": 5,
        "reps": 5,
        "weightIncrement": 5.0,
        "initialWeight": 45,
        "repsPerSet": [],
        "weight": 45
      }
    ]
  }
    """
    When I request a new workout
    Then the response should be
    """
  {
    "id": 2,
    "date": "2025-01-03",
    "type": "PULL",
    "trackedExercises": [
      {
        "id": 2,
        "name": "Deadlift",
        "type": "PULL",
        "sets": 5,
        "reps": 5,
        "weightIncrement": 5.0,
        "initialWeight": 45,
        "repsPerSet": [],
        "weight": 45
      }
    ]
  }
    """

    When I request a new workout
    Then the response should be
    """
  {
    "id": 3,
    "date": "2025-01-04",
    "type": "LEG",
    "trackedExercises": [
      {
        "id": 3,
        "name": "Squat",
        "type": "LEG",
        "sets": 5,
        "reps": 5,
        "weightIncrement": 5.0,
        "initialWeight": 45,
        "repsPerSet": [],
        "weight": 45
      }
    ]
  }
    """
