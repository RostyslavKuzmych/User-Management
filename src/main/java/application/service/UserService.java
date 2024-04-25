package application.service;

import application.dto.UserFindRequestDto;
import application.dto.UserRequestDto;
import application.dto.UserResponseDto;
import application.dto.UserUpdateRequestDto;
import java.util.List;

public interface UserService {

    UserResponseDto registerUser(UserRequestDto userRequestDto);

    UserResponseDto updateSomeUserInformation(Long id, UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto updateAllUserInformation(Long id, UserRequestDto userRequestDto);

    void deleteUser(Long id);

    List<UserResponseDto> getAllUsersByBirthDateRange(UserFindRequestDto userFindRequestDto);
}
