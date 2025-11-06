package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Entity
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class TrackedExercise extends BaseExercise {
    @ElementCollection
    @MapKeyColumn(name = "set_number")
    @Column(name = "reps")
    private Map<Integer, Integer> repsPerSet;
    private double weight;

    @JsonBackReference
    @ManyToOne
    @ToString.Exclude
    private Workout workout;

    public int getSuccessfulSets() {
        return (int) repsPerSet.values().stream().filter(reps -> reps >= getReps()).count();
    }
}