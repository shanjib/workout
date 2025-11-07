Feature: Workout Management

  Scenario: Create a new workout
    Given the application is running
    When I create new exercises with:
    """
    [
      {
        "name": "Bench Press",
        "type": "PUSH",
        "sets": 1,
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
    Then the response is successful
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
          "sets": 1,
          "reps": 5,
          "weightIncrement": 5.0,
          "initialWeight": 45,
          "repsPerSet": [],
          "weight": 45
        }
      ]
    }
    """
    When I complete a set
    """
    {
      "id": 1,
      "setNumber": 1,
      "repsCompleted": 5
    }
    """
    Then the response is successful
    When I request existing workout 1
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
          "sets": 1,
          "reps": 5,
          "weightIncrement": 5.0,
          "initialWeight": 45,
          "repsPerSet": {
            "1": 5
          },
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
    When I complete a set
    """
    {
      "id": 3,
      "setNumber": 1,
      "repsCompleted": 5
    }
    """
    Then the response is successful
    # new workout, previous PUSH workout was successful so weight is incremented
    When I request a new workout
    Then the response should be
    """
    {
      "id": 4,
      "date": "2025-01-05",
      "type": "PUSH",
      "trackedExercises": [
        {
          "id": 4,
          "name": "Bench Press",
          "type": "PUSH",
          "sets": 1,
          "reps": 5,
          "weightIncrement": 5.0,
          "initialWeight": 45,
          "repsPerSet": [],
          "weight": 50
        }
      ]
    }
    """
    # new workout, previous PULL had no sets at all so weight is same
    When I request a new workout
    Then the response should be
    """
    {
      "id": 5,
      "date": "2025-01-06",
      "type": "PULL",
      "trackedExercises": [
        {
          "id": 5,
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
    # new workout, previous LEG had incomplete sets so weight is same
    When I request a new workout
    Then the response should be
    """
    {
      "id": 6,
      "date": "2025-01-07",
      "type": "LEG",
      "trackedExercises": [
        {
          "id": 6,
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
