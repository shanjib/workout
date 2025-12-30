package com.shanjib.workout.steps;

import com.shanjib.workout.DemoApplication;
import com.shanjib.workout.controller.Constants;
import com.shanjib.workout.model.Workout;
import com.google.gson.*;
import io.cucumber.java.BeforeAll;
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

    @BeforeAll
    public static void theApplicationIsRunning() {
        DemoApplication.main(new String[] {});
    }

    @When("I create new exercises with:")
    public void iCreateNewExercisesWith(String json) {
        response = restTemplate.postForEntity(BASE_URL + Constants.EXERCISES, buildEntity(json), String.class);
    }

    @Then("the response is successful")
    public void theResponseIsSuccessful() {
        assertEquals(200, response.getStatusCode().value());
    }

    @When("I request a new workout")
    public void iRequestANewWorkout() {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS + Constants.NEW, String.class);
    }

    @Then("the response should be")
    public void theResponseShouldBe(String json) {
        Workout expected = gson.fromJson(json, Workout.class);
        Workout actual = gson.fromJson(response.getBody(), Workout.class);
        assertEquals(expected, actual);
    }

    @When("I complete a set")
    public void iCompleteASet(String json) {
        response = restTemplate.postForEntity(BASE_URL + Constants.EXERCISES + Constants.TRACKED_EXERCISES + Constants.UPDATE, buildEntity(json), String.class);
    }

    @When("I request existing workout {int}")
    public void iRequestExistingWorkout(int id) {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS + Constants.GET + "/" + id, String.class);
    }

    private HttpEntity<String> buildEntity(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }
}