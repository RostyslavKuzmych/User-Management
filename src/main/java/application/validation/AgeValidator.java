package application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;

public class AgeValidator implements ConstraintValidator<CheckAge, LocalDate> {
    @Value("${adult_age}")
    private Integer adultAge;

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext
            constraintValidatorContext) {
        return LocalDate.now().isAfter(localDate.plusYears(adultAge));
    }
}
