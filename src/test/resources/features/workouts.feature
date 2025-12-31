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
      "initialWeight":45
    },
    {
      "name":"Deadlift",
      "type":"PULL",
      "sets":5,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45
    },
    {
      "name":"Squat",
      "type":"LEG",
      "sets":5,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45
    }
  ]
}
    """
    Then the response is successful
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
      "initialWeight":45
    },
    {
      "id":2,
      "name":"Deadlift",
      "type":"PULL",
      "sets":5,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45
    },
    {
      "id":3,
      "name":"Squat",
      "type":"LEG",
      "sets":5,
      "reps":5,
      "weightIncrement":5.0,
      "initialWeight":45
    }
  ]
}
    """
    When I update exercise 1 with
    """
{
  "name":"Bench Press",
  "type":"PUSH",
  "sets":3,
  "reps":5,
  "weightIncrement":5.0,
  "initialWeight":45
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
    "initialWeight":45
  }
}
    """
    When I request the next workout details
    Then the 3 response should be
    """
{
  "type":"PUSH",
  "date":"2025-01-02",
  "exerciseToWeight":{
    "Bench Press":45
  }
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
        "name":"Bench Press",
        "type":"PUSH",
        "weight":60,
        "setsToReps":{
          "1":0,
          "2":0,
          "3":0
        }
      }
    ]
  }
}
    """
    When I update workout 1
    """
{
  "type":"PUSH",
  "date":"2025-01-02",
  "exercises":[
    {
      "name":"Bench Press",
      "type":"PUSH",
      "weight":60,
      "setsToReps":{
        "1":5,
        "2":5,
        "3":5
      }
    }
  ]
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
        "name":"Bench Press",
        "type":"PUSH",
        "weight":60,
        "setsToReps":{
          "1":5,
          "2":5,
          "3":5
        }
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
  "exerciseToWeight":{
    "Deadlift":45
  }
}
    """
