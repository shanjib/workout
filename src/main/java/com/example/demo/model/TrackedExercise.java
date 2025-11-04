package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class TrackedExercise extends Exercise {
    @ElementCollection
    private List<Integer> repsPerSet;
    private double weight;

    @JsonBackReference
    @ManyToOne
    private Workout workout;

    public int getSuccessfulSets() {
        return (int) repsPerSet.stream().filter(reps -> reps >= getReps()).count();
    }
}