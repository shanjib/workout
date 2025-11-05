package com.example.demo.steps;

import com.example.demo.DemoApplication;
import com.example.demo.controller.Constants;
import com.example.demo.model.Workout;
import com.google.gson.*;
import io.cucumber.java.en.Given;
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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@CucumberContextConfiguration
public class WorkoutSteps {
    private final String BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, ctx) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, ctx) -> LocalDate.parse(json.getAsString()))
            .create();

    private ResponseEntity<String> response;

    @Given("the application is running")
    public void theApplicationIsRunning() {
        DemoApplication.main(new String[] {});
    }

    @When("I request a new workout")
    public void iRequestANewWorkout() {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS + Constants.NEW, String.class);
    }

    @When("I create new exercises with:")
    public void iCreateNewExercisesWith(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        response = restTemplate.postForEntity(BASE_URL + Constants.EXERCISES + Constants.NEW, entity, String.class);
    }

    @Then("the exercises are saved successfully")
    public void theExercisesAreSavedSuccessfully() {
        assertEquals(200, response.getStatusCode().value());
    }

    @Then("the response should be")
    public void theResponseShouldBe(String json) {
        Workout expected = gson.fromJson(json, Workout.class);
        Workout actual = gson.fromJson(response.getBody(), Workout.class);
        assertEquals(expected, actual);
    }
}