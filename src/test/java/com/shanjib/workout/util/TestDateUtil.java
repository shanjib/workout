package com.shanjib.workout.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("dateUtil")
@Profile("test")
public class TestDateUtil extends DateUtil {
    private LocalDate currentDate = LocalDate.of(2025, 1, 1);

    public LocalDate getCurrentDate() {
        currentDate = currentDate.plusDays(1);
        return currentDate;
    }
}
