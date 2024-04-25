package application.dto;

import application.validation.CheckAge;
import application.validation.PhoneCheck;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserRequestDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @CheckAge
    private LocalDate birthDate;
    private String address;
    @PhoneCheck
    private String phoneNumber;
}
