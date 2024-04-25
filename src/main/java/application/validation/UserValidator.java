package application.validation;

import application.dto.UserFindRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<CorrectUserInputs, Object> {
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        UserFindRequestDto userFindRequestDto = (UserFindRequestDto) object;
        return userFindRequestDto.getFrom().isBefore(userFindRequestDto.getTo());
    }
}
