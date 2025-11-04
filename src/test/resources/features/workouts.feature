Feature: Workout Management

  Scenario: Create a new workout
    Given the application is running
    When I create new exercises with:
      | name        | type | sets | reps | weightIncrement | initialWeight |
      | Bench Press | PUSH | 5    | 5    | 5.0             | 45            |
      | Squat       | LEG  | 5    | 5    | 5.0             | 45            |
    Then the exercises are saved successfully
    And the exercise response contains:
      | name        | type | sets | reps | weightIncrement | initialWeight |
      | Bench Press | PUSH | 5    | 5    | 5.0             | 45            |
      | Squat       | LEG  | 5    | 5    | 5.0             | 45            |
    When I request a new workout
    Then the response status code should be 200
    And the workout should have today's date

