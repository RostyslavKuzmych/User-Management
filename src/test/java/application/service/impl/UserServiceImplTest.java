package application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import application.dto.UserFindRequestDto;
import application.dto.UserRequestDto;
import application.dto.UserResponseDto;
import application.dto.UserUpdateRequestDto;
import application.mapper.UserMapper;
import application.model.User;
import application.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final String ALICE_PHONE_NUMBER = "+380985454523";
    private static final String ALICE_EMAIL = "alice@gmail.com";
    private static final LocalDate FROM = LocalDate.now().minusYears(30);
    private static final LocalDate TO = LocalDate.now().minusYears(18);
    private static final String ALICE_FIRST_NAME = "Alice";
    private static final String ALICE_LAST_NAME = "Johnson";
    private static final LocalDate ALICE_BIRTH_DATE = LocalDate.now().minusYears(20);
    private static final String ALICE_ADDRESS = "Lisova 45";
    private static final String OLIVER_PHONE_NUMBER = "+380985456723";
    private static final String OLIVER_EMAIL = "oliver@gmail.com";
    private static final String OLIVER_FIRST_NAME = "Oliver";
    private static final String OLIVER_LAST_NAME = "Adams";
    private static final LocalDate OLIVER_BIRTH_DATE = LocalDate.now().minusYears(26);
    private static final String OLIVER_ADDRESS = "Mudryk 34";
    private static final Long ALICE_ID = 1L;
    private static final String JOHN_EMAIL = "john@gmail.com";
    private static final String JOHN_FIRST_NAME = "John";
    private static final String JOHN_LAST_NAME = "Deck";
    private static UserRequestDto aliceRequestDto;
    private static User alice;
    private static UserRequestDto oliverRequestDto;
    private static User oliver;
    private static UserResponseDto oliverResponseDto;
    private static UserResponseDto aliceResponseDto;
    private static UserUpdateRequestDto aliceUpdateRequestDto;
    private static UserFindRequestDto userFindRequestDto;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeAll
    static void beforeAll() {
        aliceRequestDto = new UserRequestDto().setAddress(ALICE_ADDRESS)
                .setEmail(ALICE_EMAIL)
                .setBirthDate(ALICE_BIRTH_DATE)
                .setFirstName(ALICE_FIRST_NAME)
                .setLastName(ALICE_LAST_NAME)
                .setPhoneNumber(ALICE_PHONE_NUMBER);
        alice = new User().setAddress(ALICE_ADDRESS)
                .setEmail(ALICE_EMAIL)
                .setBirthDate(ALICE_BIRTH_DATE)
                .setFirstName(ALICE_FIRST_NAME)
                .setLastName(ALICE_LAST_NAME)
                .setPhoneNumber(ALICE_PHONE_NUMBER)
                .setId(ALICE_ID);
        oliverRequestDto = new UserRequestDto().setAddress(OLIVER_ADDRESS)
                .setEmail(OLIVER_EMAIL)
                .setBirthDate(OLIVER_BIRTH_DATE)
                .setFirstName(OLIVER_FIRST_NAME)
                .setLastName(OLIVER_LAST_NAME)
                .setPhoneNumber(OLIVER_PHONE_NUMBER);
        oliver = new User().setAddress(OLIVER_ADDRESS)
                .setEmail(OLIVER_EMAIL)
                .setBirthDate(OLIVER_BIRTH_DATE)
                .setFirstName(OLIVER_FIRST_NAME)
                .setLastName(OLIVER_LAST_NAME)
                .setPhoneNumber(OLIVER_PHONE_NUMBER)
                .setId(ALICE_ID);
        aliceUpdateRequestDto = new UserUpdateRequestDto().setLastName(JOHN_LAST_NAME)
                .setFirstName(JOHN_FIRST_NAME)
                .setEmail(JOHN_EMAIL);
        userFindRequestDto = new UserFindRequestDto().setFrom(FROM).setTo(TO);
    }

    @BeforeEach
    void setUp() {
        aliceResponseDto = new UserResponseDto().setAddress(ALICE_ADDRESS)
                .setEmail(ALICE_EMAIL)
                .setId(ALICE_ID)
                .setBirthDate(ALICE_BIRTH_DATE)
                .setFirstName(ALICE_FIRST_NAME)
                .setLastName(ALICE_LAST_NAME)
                .setPhoneNumber(ALICE_PHONE_NUMBER);
        oliverResponseDto = new UserResponseDto().setAddress(OLIVER_ADDRESS)
                .setEmail(OLIVER_EMAIL)
                .setBirthDate(OLIVER_BIRTH_DATE)
                .setFirstName(OLIVER_FIRST_NAME)
                .setLastName(OLIVER_LAST_NAME)
                .setPhoneNumber(OLIVER_PHONE_NUMBER)
                .setId(ALICE_ID);
    }

    @Test
    @DisplayName("""
            Verify registerUser() method
            """)
    void registerUser() {
        // when
        when(userRepository.findUserByEmail(ALICE_EMAIL)).thenReturn(Optional.empty());
        when(userMapper.toEntity(aliceRequestDto)).thenReturn(alice);
        when(userRepository.save(alice)).thenReturn(alice);
        when(userMapper.toDto(alice)).thenReturn(aliceResponseDto);

        // then
        UserResponseDto actual = userServiceImpl.registerUser(aliceRequestDto);
        UserResponseDto expected = aliceResponseDto;
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify updateSomeUserInformation() method
            """)
    void updateSomeUserInformation() {
        // given
        User john = alice.setFirstName(JOHN_FIRST_NAME).setLastName(JOHN_LAST_NAME)
                .setEmail(JOHN_EMAIL);
        UserResponseDto johnResponseDto = aliceResponseDto.setFirstName(JOHN_FIRST_NAME)
                .setLastName(JOHN_LAST_NAME).setEmail(JOHN_EMAIL);

        // when
        when(userRepository.findById(ALICE_ID)).thenReturn(Optional.of(alice));
        when(userRepository.save(john)).thenReturn(john);
        when(userMapper.toDto(john)).thenReturn(johnResponseDto);

        // then
        UserResponseDto actual = userServiceImpl
                .updateSomeUserInformation(ALICE_ID, aliceUpdateRequestDto);
        UserResponseDto expected = johnResponseDto;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify updateAllUserInformation() method
            """)
    void updateAllUserInformation() {
        // when
        when(userRepository.findById(ALICE_ID)).thenReturn(Optional.of(alice));
        when(userMapper.toEntity(oliverRequestDto)).thenReturn(oliver);
        when(userRepository.save(oliver)).thenReturn(oliver);
        when(userMapper.toDto(oliver)).thenReturn(oliverResponseDto);

        // then
        UserResponseDto actual = userServiceImpl
                .updateAllUserInformation(ALICE_ID, oliverRequestDto);
        UserResponseDto expected = oliverResponseDto;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify getAllUsersByBirthDateRange() method
            """)
    void getAllUsersByBirthDateRange() {
        // when
        when(userRepository.getAllByBirthDateAfterAndBirthDateBefore(FROM, TO))
                .thenReturn(List.of(alice));
        when(userMapper.toDto(alice)).thenReturn(aliceResponseDto);

        // then
        List<UserResponseDto> actual = userServiceImpl
                .getAllUsersByBirthDateRange(userFindRequestDto);
        List<UserResponseDto> expected = List.of(aliceResponseDto);
        assertEquals(actual, expected);
    }
}
