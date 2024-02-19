package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record BirthDate(LocalDate date) {
    public Long getAge(LocalDate date) {
        return ChronoUnit.YEARS.between(date, LocalDate.now());
    }
}
