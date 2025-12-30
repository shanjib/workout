package com.shanjib.workout.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
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

    public void setupData(int set) {
        if (repsPerSet == null) {
            repsPerSet = new HashMap<>();
        }
        if (!repsPerSet.containsKey(set)) {
            repsPerSet.put(set, 0);
        }
    }

    public void updateRep(int set, boolean successfulRep) {
        int reps = repsPerSet.get(set);
        if (successfulRep) {
            repsPerSet.put(set, reps + 1);
        } else {
            repsPerSet.put(set, reps - 1);
        }
    }

    public void updateSet(int set, boolean successfulSet) {
        if (successfulSet) {
            repsPerSet.put(set, getReps());
        } else {
            repsPerSet.put(set, 0);
        }
    }

    public boolean isSuccessful() {
        return repsPerSet.values().stream().filter(reps -> reps >= getReps()).count() >= getSets();
    }
}