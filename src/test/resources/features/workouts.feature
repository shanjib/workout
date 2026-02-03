Feature: Workout Management

  Scenario: Workout Lifecycle
    When I create new exercises with
    """
{
  "exercises":[
    {
      "name":"Bench Press",
      "type":"PUSH",
      "sets":1,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
    },
    {
      "name":"Deadlift",
      "type":"PULL",
      "sets":3,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
    },
    {
      "name":"Squat",
      "type":"LEG",
      "sets":3,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
    }
  ]
}
    """
    Then the 6 response should be
    """
{
  "exercises":[
    {
      "name":"Bench Press",
      "success":true,
      "id":1
    },
    {
      "name":"Deadlift",
      "success":true,
      "id":2
    },
    {
      "name":"Squat",
      "success":true,
      "id":3
    }
  ]
}
    """
    When I get exercises
    Then the 1 response should be
    """
{
  "exercises":[
    {
      "id":1,
      "name":"Bench Press",
      "type":"PUSH",
      "sets":1,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
    },
    {
      "id":2,
      "name":"Deadlift",
      "type":"PULL",
      "sets":3,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
    },
    {
      "id":3,
      "name":"Squat",
      "type":"LEG",
      "sets":3,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
    }
  ]
}
    """
    When I update exercise 1 with
    """
{
  "exercise":{
    "name":"Bench Press",
    "type":"PUSH",
    "sets":3,
    "reps":5,
    "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1
  }
}
    """
    Then the response is successful
    When I get exercise 1
    Then the 2 response should be
    """
{
  "exercise":{
    "id":1,
    "name":"Bench Press",
    "type":"PUSH",
    "sets":3,
    "reps":5,
    "weightIncrement":5.0,
    "initialWeight":45,
    "numberOfWeights":1
  }
}
    """
    When I request the next workout details
    Then the 3 response should be
    """
{
  "type":"PUSH",
  "date":"2025-01-02",
  "exercises":[
    {
      "id":1,
      "name":"Bench Press",
      "type":"PUSH",
      "sets":3,
      "reps":5,
      "weight": 45,
      "barExercise": true,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1,
      "notes": "This is the first time this exercise has been done."
    }
  ]
}
    """
    When I confirm the next workout details
    """
{
  "type":"PUSH",
  "date":"2025-01-02",
  "exerciseToWeight":{
    "Bench Press":60
  }
}
    """
    Then the 4 response should be
    """
{
  "id":1
}
    """
    When I get workout 1
    Then the 5 response should be
    """
{
  "workout":{
    "type":"PUSH",
    "date":"2025-01-02",
    "exercises":[
      {
        "id":1,
        "name":"Bench Press",
        "type":"PUSH",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":60,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      }
    ]
  }
}
    """
    When I update workout 1
    """
{
  "workout":{
    "type":"PUSH",
    "date":"2025-01-02",
    "exercises":[
      {
        "id":1,
        "name":"Bench Press",
        "type":"PUSH",
        "weight":60,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":5
        },
        "notes":"very easy"
      }
    ]
  }
}
    """
    Then the response is successful
    When I get workout 1
    Then the 5 response should be
    """
{
  "workout":{
    "type":"PUSH",
    "date":"2025-01-02",
    "exercises":[
      {
        "id":1,
        "name":"Bench Press",
        "type":"PUSH",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":60,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":5
        },
        "notes":"very easy"
      }
    ]
  }
}
    """
    When I request the next workout details
    Then the 3 response should be
    """
{
  "type":"PULL",
  "date":"2025-01-03",
  "exercises":[
    {
      "id":2,
      "name":"Deadlift",
      "type":"PULL",
      "sets":3,
      "reps":5,
      "weight": 45,
      "barExercise": true,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1,
      "notes": "This is the first time this exercise has been done."
    }
  ]
}
    """
    When I confirm the next workout details
    """
{
  "type":"PULL",
  "date":"2025-01-03",
  "exerciseToWeight":{
    "Deadlift":80
  }
}
    """
    Then the 4 response should be
    """
{
  "id":2
}
    """
    When I get workout 2
    Then the 5 response should be
    """
{
  "workout":{
    "type":"PULL",
    "date":"2025-01-03",
    "exercises":[
      {
        "id":2,
        "name":"Deadlift",
        "type":"PULL",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":80,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      }
    ]
  }
}
    """
    When I update workout 2
    """
{
  "workout":{
    "type":"PULL",
    "date":"2025-01-03",
    "exercises":[
      {
        "id":2,
        "name":"Deadlift",
        "type":"PULL",
        "weight":80,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":5
        },
        "notes":null
      }
    ]
  }
}
    """
    Then the response is successful
    When I get workout 2
    Then the 5 response should be
    """
{
  "workout":{
    "type":"PULL",
    "date":"2025-01-03",
    "exercises":[
      {
        "id":2,
        "name":"Deadlift",
        "type":"PULL",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":80,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":5
        },
        "notes":null
      }
    ]
  }
}
    """
    When I request the next workout details
    Then the 3 response should be
    """
{
  "type":"LEG",
  "date":"2025-01-04",
  "exercises":[
    {
      "id":3,
      "name":"Squat",
      "type":"LEG",
      "sets":3,
      "reps":5,
      "weight": 45,
      "barExercise": true,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1,
      "notes": "This is the first time this exercise has been done."
    }
  ]
}
    """
    When I confirm the next workout details
    """
{
  "type":"LEG",
  "date":"2025-01-04",
  "exerciseToWeight":{
    "Squat":45
  }
}
    """
    Then the 4 response should be
    """
{
  "id":3
}
    """
    When I get workout 3
    Then the 5 response should be
    """
{
  "workout":{
    "type":"LEG",
    "date":"2025-01-04",
    "exercises":[
      {
        "id":3,
        "name":"Squat",
        "type":"LEG",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":45,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      }
    ]
  }
}
    """
    When I update workout 3
    """
{
  "workout":{
    "type":"LEG",
    "date":"2025-01-04",
    "exercises":[
      {
        "id":3,
        "name":"Squat",
        "type":"LEG",
        "weight":45,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":3
        },
        "notes":null
      }
    ]
  }
}
    """
    Then the response is successful
    When I get workout 3
    Then the 5 response should be
    """
{
  "workout":{
    "type":"LEG",
    "date":"2025-01-04",
    "exercises":[
      {
        "id":3,
        "name":"Squat",
        "type":"LEG",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":45,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":3
        },
        "notes":null
      }
    ]
  }
}
    """
    When I request the next workout details
    Then the 3 response should be
    """
{
  "type":"PUSH",
  "date":"2025-01-05",
  "exercises":[
    {
      "id":1,
      "name":"Bench Press",
      "type":"PUSH",
      "sets":3,
      "reps":5,
      "weight": 60,
      "barExercise": true,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1,
      "setsToReps":{
        "1":5,
        "2":5,
        "3":5
      },
      "notes": "very easy All sets completed successfully last time."
    }
  ]
}
    """
    When I get workouts
    Then the 7 response should be
    """
{
  "latestWorkouts":[
    {
      "date":"2025-01-04",
      "type":"LEG",
      "id":3
    },
    {
      "date":"2025-01-03",
      "type":"PULL",
      "id":2
    },
    {
      "date":"2025-01-02",
      "type":"PUSH",
      "id":1
    }
  ]
}
    """

  Scenario: Multi Exercise
    # This scenario is dependent on the previous scenario
    When I create new exercises with
    """
{
  "exercises":[
    {
      "name":"Lateral Raises",
      "type":"PUSH",
      "sets":3,
      "reps":10,
      "weightIncrement":5.0,
      "initialWeight":0,
      "numberOfWeights":2
    },
    {
      "name":"Tricep Pushdown",
      "type":"PUSH",
      "sets":3,
      "reps":10,
      "weightIncrement":5.0,
      "initialWeight":0,
      "numberOfWeights":1
    }
  ]
}
    """
    When I request the next workout details
    Then the 3 response should be
    """
{
  "type":"PUSH",
  "date":"2025-01-06",
  "exercises":[
    {
      "id":1,
      "name":"Bench Press",
      "type":"PUSH",
      "sets":3,
      "reps":5,
      "weight": 60,
      "barExercise": true,
      "weightIncrement":5.0,
      "initialWeight":45,
      "numberOfWeights":1,
      "setsToReps":{
        "1":5,
        "2":5,
        "3":5
      },
      "notes": "very easy All sets completed successfully last time."
    }
  ]
}
    """
    When I confirm the next workout details
    """
{
  "type":"PUSH",
  "date":"2025-01-06",
  "exerciseToWeight":{
    "Bench Press":70,
    "Lateral Raises":20
  }
}
    """
    Then the 4 response should be
    """
{
  "id":4
}
    """
    When I get workout 4
    Then the 5 response should be
    """
{
  "workout":{
    "type":"PUSH",
    "date":"2025-01-06",
    "exercises":[
      {
        "id":4,
        "name":"Bench Press",
        "type":"PUSH",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":70,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      },
      {
        "id":5,
        "name":"Lateral Raises",
        "type":"PUSH",
        "initialWeight": 0,
        "numberOfWeights": 2,
        "weight":20,
        "reps":10,
        "barExercise":false,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      }
    ]
  }
}
    """
    When I update workout 4
    """
{
  "newExerciseToWeight":{
    "Tricep Pushdown":30
  },
  "workout":{
    "type":"PUSH",
    "date":"2025-01-06",
    "exercises":[
      {
        "id":4,
        "name":"Bench Press",
        "type":"PUSH",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":70,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      },
      {
        "id":5,
        "name":"Lateral Raises",
        "type":"PUSH",
        "initialWeight": 0,
        "numberOfWeights": 2,
        "weight":20,
        "reps":10,
        "barExercise":false,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      }
    ]
  }
}
    """
    Then the response is successful
    When I get workout 4
    Then the 5 response should be
    """
{
  "workout":{
    "type":"PUSH",
    "date":"2025-01-06",
    "exercises":[
      {
        "id":4,
        "name":"Bench Press",
        "type":"PUSH",
        "initialWeight": 45,
        "numberOfWeights":1,
        "weight":70,
        "reps":5,
        "barExercise":true,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      },
      {
        "id":5,
        "name":"Lateral Raises",
        "type":"PUSH",
        "initialWeight": 0,
        "numberOfWeights": 2,
        "weight":20,
        "reps":10,
        "barExercise":false,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      },
      {
        "id":6,
        "name":"Tricep Pushdown",
        "type":"PUSH",
        "initialWeight": 0,
        "numberOfWeights": 1,
        "weight":30,
        "reps":10,
        "barExercise":false,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        },
        "notes":null
      }
    ]
  }
}
    """
