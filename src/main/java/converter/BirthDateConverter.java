package converter;

import domain.BirthDate;
import jakarta.persistence.AttributeConverter;

import java.sql.Date;

import static java.util.Optional.ofNullable;

public class BirthDateConverter implements AttributeConverter<BirthDate, Date> {
    @Override
    public Date convertToDatabaseColumn(BirthDate birthDate) {
        return ofNullable(birthDate)
                .map(BirthDate::date)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public BirthDate convertToEntityAttribute(Date date) {
        return ofNullable(date)
                .map(Date::toLocalDate)
                .map(BirthDate::new)
                .orElse(null);
    }
}
