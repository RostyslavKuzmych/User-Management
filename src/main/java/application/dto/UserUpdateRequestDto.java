package application.dto;

import application.validation.CheckAge;
import application.validation.PhoneCheck;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserUpdateRequestDto {
    @Email
    private String email;
    private String firstName;
    private String lastName;
    @CheckAge
    private LocalDate birthDate;
    private String address;
    @PhoneCheck
    private String phoneNumber;
}
