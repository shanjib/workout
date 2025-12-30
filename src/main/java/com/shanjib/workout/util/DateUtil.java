package com.shanjib.workout.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Profile("!test")
public class DateUtil {
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
