package application.dto;

import application.validation.CorrectUserInputs;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@CorrectUserInputs
public class UserFindRequestDto {
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;
}
