package application.service.impl;

import application.dto.UserFindRequestDto;
import application.dto.UserRequestDto;
import application.dto.UserResponseDto;
import application.dto.UserUpdateRequestDto;
import application.exception.EntityNotFoundException;
import application.exception.RegistrationException;
import application.mapper.UserMapper;
import application.model.User;
import application.repository.UserRepository;
import application.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_FIND_EXCEPTION = "Can't find a user with id ";
    private static final String REGISTRATION_MESSAGE = "User with %s is already registered";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (userRepository.findUserByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException(String.format(REGISTRATION_MESSAGE,
                    userRequestDto.getEmail()));
        }
        User user = userMapper.toEntity(userRequestDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto updateSomeUserInformation(Long id,
                                                     UserUpdateRequestDto userUpdateRequestDto) {
        User userFromDb = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(USER_FIND_EXCEPTION + id));
        return userMapper.toDto(userRepository.save(updateSomeUserInfo(userFromDb,
                userUpdateRequestDto)));
    }

    @Override
    public UserResponseDto updateAllUserInformation(Long id, UserRequestDto userRequestDto) {
        if (userRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(USER_FIND_EXCEPTION + id);
        }
        User user = userMapper.toEntity(userRequestDto).setId(id);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDto> getAllUsersByBirthDateRange(UserFindRequestDto
                                                                         userFindRequestDto) {
        return userRepository.getAllByBirthDateAfterAndBirthDateBefore(userFindRequestDto.getFrom(),
                userFindRequestDto.getTo()).stream().map(userMapper::toDto).toList();
    }

    private User updateSomeUserInfo(User user, UserUpdateRequestDto userUpdateRequestDto) {
        if (userUpdateRequestDto.getAddress() != null) {
            user.setAddress(userUpdateRequestDto.getAddress());
        }
        if (userUpdateRequestDto.getEmail() != null) {
            user.setEmail(userUpdateRequestDto.getEmail());
        }
        if (userUpdateRequestDto.getBirthDate() != null) {
            user.setBirthDate(userUpdateRequestDto.getBirthDate());
        }
        if (userUpdateRequestDto.getFirstName() != null) {
            user.setFirstName(userUpdateRequestDto.getFirstName());
        }
        if (userUpdateRequestDto.getLastName() != null) {
            user.setLastName(userUpdateRequestDto.getLastName());
        }
        if (userUpdateRequestDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
        }

        return user;
    }
}
