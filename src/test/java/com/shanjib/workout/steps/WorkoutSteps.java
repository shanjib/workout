package com.shanjib.workout.steps;

import com.shanjib.workout.DemoApplication;
import com.shanjib.workout.controller.Constants;
import com.shanjib.workout.dto.*;
import com.google.gson.*;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@CucumberContextConfiguration
public class WorkoutSteps {
    private final String BASE_URL = "http://localhost:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    private ResponseEntity<String> response;
    private boolean success = true;

    @BeforeAll
    public static void theApplicationIsRunning() {
        DemoApplication.main(new String[] {});
    }

    @After
    public void after() {
        assertTrue(success);
    }

    @When("I create new exercises with")
    public void iCreateNewExercisesWith(String json) {
        response = restTemplate.postForEntity(BASE_URL + Constants.EXERCISES, buildEntity(json), String.class);
    }

    @When("I get exercises")
    public void iGetExercises() {
        response = restTemplate.getForEntity(BASE_URL + Constants.EXERCISES, String.class);
    }

    @When("I update exercise {int} with")
    public void iUpdateExerciseWith(int id, String json) {
        restTemplate.put(BASE_URL + Constants.EXERCISES + "/" + id, buildEntity(json), String.class);
    }

    @When("I get exercise {int}")
    public void iGetExercise(int id) {
        response = restTemplate.getForEntity(BASE_URL + Constants.EXERCISES + "/" + id, String.class);
    }

    @When("I request the next workout details")
    public void iRequestTheNextWorkoutDetails() {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS + Constants.NEXT, String.class);
    }

    @When("I confirm the next workout details")
    public void iConfirmTheNextWorkoutDetails(String json) {
        response = restTemplate.postForEntity(BASE_URL + Constants.WORKOUTS, buildEntity(json), String.class);
    }

    @When("I get workout {int}")
    public void iGetWorkout(int id) {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS + "/" + id, String.class);
    }

    @When("I update workout {int}")
    public void iUpdateWorkout(int id, String json) {
        restTemplate.put(BASE_URL + Constants.WORKOUTS + "/" + id, buildEntity(json), String.class);
    }

    @When("I get workouts")
    public void iGetWorkouts() {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS, String.class);
    }

    @Then("the response is successful")
    public void theResponseIsSuccessful() {
        if (!objectsAreEqual(200, response.getStatusCode().value())) {
            success = false;
            log.error("Failed on status code");
        }
    }

    @Then("the {int} response should be")
    public void theResponseShouldBe(int type, String json) {
        if (type == 1) {
            check(json, response.getBody(), GetExercisesResponseDTO.class);
        } else if (type == 2) {
            check(json, response.getBody(), GetExerciseResponseDTO.class);
        } else if (type == 3) {
            check(json, response.getBody(), GetNextWorkoutDetailsResponseDTO.class);
        } else if (type == 4) {
            check(json, response.getBody(), CreateWorkoutResponseDTO.class);
        } else if (type == 5) {
            check(json, response.getBody(), GetWorkoutResponseDTO.class);
        } else if (type == 6) {
            check(json, response.getBody(), CreateExerciseResponseDTO.class);
        } else if (type == 7) {
            check(json, response.getBody(), GetLatestWorkoutsResponseDTO.class);
        } else {
            success = false;
            log.error("Unable to handle object {} with json {}", type, json);
        }
    }

    private <T> void check(String expected, String actual, Class<T> clazz) {
        T e = gson.fromJson(expected, clazz);
        T a = gson.fromJson(actual, clazz);
        if (!objectsAreEqual(e, a)) {
            success = false;
            log.error("Failed on {}\nexpected: {}\nactual: {}\n", clazz, e, a);
        }
    }

    private HttpEntity<String> buildEntity(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }

    private boolean objectsAreEqual(Object obj1, Object obj2) {
        if (obj1 == null) {
            return (obj2 == null);
        }
        return obj1.equals(obj2);
    }
}