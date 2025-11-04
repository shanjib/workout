package com.example.demo.steps;

import com.example.demo.DemoApplication;
import com.example.demo.controller.Constants;
import com.example.demo.model.Exercise;
import com.example.demo.model.WorkoutType;
import io.cucumber.datatable.DataTable;
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

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@CucumberContextConfiguration
public class WorkoutSteps {
    private final String BASE_URL = "http://localhost:8080";
    private final RestTemplate restTemplate = new RestTemplate();
    private final TestCreator creator = new TestCreator();
    private ResponseEntity<String> response;

    @Given("the application is running")
    public void theApplicationIsRunning() {
        DemoApplication.main(new String[] {});
    }

    @When("I request a new workout")
    public void iRequestANewWorkout() {
        response = restTemplate.getForEntity(BASE_URL + Constants.WORKOUTS + Constants.NEW, String.class);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.getStatusCodeValue());
    }

    @Then("the workout should have today's date")
    public void theWorkoutShouldHaveTodaysDate() {
        assertTrue(response.getBody().contains(LocalDate.now().toString()));
    }
    @When("I create new exercises with:")
    public void iCreateNewExercisesWith(DataTable table) {
        List<Map<String, String>> data = table.asMaps();

        List<String> exercises = data.stream().map(row -> String.format(
                "{ \"name\": \"%s\", \"type\": \"%s\", \"sets\": %s, \"reps\": %s, \"weightIncrement\": %s, \"initialWeight\": %s }",
                row.get("name"),
                row.get("type"),
                row.get("sets"),
                row.get("reps"),
                row.get("weightIncrement"),
                row.get("initialWeight")
        )).collect(Collectors.toList());

        String jsonPayload = "[" + String.join(",", exercises) + "]";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        response = restTemplate.postForEntity(BASE_URL + Constants.EXERCISES + Constants.NEW, entity, String.class);
    }

    @Then("the exercises are saved successfully")
    public void theExercisesAreSavedSuccessfully() {
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("the exercise response contains:")
    public void theExerciseResponseContains(DataTable table) {
        theResponseContains(table, Exercise.class);
    }

    public void theResponseContains(DataTable table, Class clazz) {
        String responseBody = response.getBody();
        assert responseBody != null;
        boolean mismatch = false;

        for (Map<String, String> row : table.asMaps()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                if (!row.containsKey(name)) continue;

                String value = row.get(name);

                String json;
                if (field.getType() == String.class || field.getType() == WorkoutType.class) {
                    json = "\"%s\":\"%s\"";
                } else {
                    json = "\"%s\":%s";
                }
                String formattedJson = String.format(json, name, value);
                if (!responseBody.contains(formattedJson)) {
                    log.error("expected -- {}:{}", name, value);
                    mismatch = true;
                }
            }
        }
        if (mismatch) {
            log.error(responseBody);
            fail();
        }
    }
}