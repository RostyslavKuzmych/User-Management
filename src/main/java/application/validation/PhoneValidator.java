package application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhoneCheck, String> {
    private static final String PHONE_FORMAT = "^\\+380\\d{9}$";
    private static final Pattern pattern = Pattern.compile(PHONE_FORMAT);

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone == null || pattern.matcher(phone).matches();
    }
}
