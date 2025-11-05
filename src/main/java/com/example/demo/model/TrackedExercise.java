package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class TrackedExercise extends Exercise {
    @ElementCollection
    private List<Integer> repsPerSet;
    private double weight;

    @JsonBackReference
    @ManyToOne
    @ToString.Exclude
    private Workout workout;

    public int getSuccessfulSets() {
        return (int) repsPerSet.stream().filter(reps -> reps >= getReps()).count();
    }
}