package application.controller;

import application.dto.UserFindRequestDto;
import application.dto.UserRequestDto;
import application.dto.UserResponseDto;
import application.dto.UserUpdateRequestDto;
import application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users management", description = "Endpoints for users management")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Register a new user", description = "Endpoint for registering a new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.registerUser(userRequestDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update piece of user's information",
            description = "Endpoint for updating piece of user's information")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateSomeUserInformation(@PathVariable Long id,
                                                     @Valid @RequestBody
                                                     UserUpdateRequestDto userUpdateRequestDto) {
        return userService.updateSomeUserInformation(id, userUpdateRequestDto);
    }

    @GetMapping("/dateRange")
    @Operation(summary = "Get all users by birth date range",
            description = "Endpoint for getting all users by birth date range")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsersByBirthDateRange(@Valid @RequestBody UserFindRequestDto
                                                                     userFindRequestDto) {
        return userService.getAllUsersByBirthDateRange(userFindRequestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update all user's information",
            description = "Endpoint for updating all user's information")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateAllUserInformation(@PathVariable Long id,
                                                    @Valid @RequestBody
                                                    UserRequestDto userRequestDto) {
        return userService.updateAllUserInformation(id, userRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user", description = "Endpoint for deleting a user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
