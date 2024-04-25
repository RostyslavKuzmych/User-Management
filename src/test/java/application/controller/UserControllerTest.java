package application.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import application.dto.UserFindRequestDto;
import application.dto.UserRequestDto;
import application.dto.UserResponseDto;
import application.dto.UserUpdateRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    protected static MockMvc mockMvc;
    private static final String REMOVE_JOHN = "delete_john_from_users_table.sql";
    private static final String ID = "id";
    private static final String REMOVE_ALISSON = "delete_alisson_from_users_table.sql";
    private static final String BASE_PATH = "classpath:database/users/";
    private static final String BASE_URL = "/api/users";
    private static final String DATE_RANGE = "/dateRange";
    private static final String ADD_ALICE = "add_alice_to_users_table.sql";
    private static final String REMOVE_ALICE = "delete_alice_from_users_table.sql";
    private static final String ALICE_NEW_EMAIL = "alisson@gmail.com";
    private static final String ALICE_NEW_FIRST_NAME = "Alisson";
    private static final String ALICE_NEW_PHONE_NUMBER = "+380986574534";
    private static final String ALICE_NEW_LAST_NAME = "Kred";
    private static final String ALICE_PHONE_NUMBER = "+380985454523";
    private static final String ALICE_EMAIL = "alice@gmail.com";
    private static final String ALICE_FIRST_NAME = "Alice";
    private static final String ALICE_LAST_NAME = "Johnson";
    private static final LocalDate ALICE_BIRTH_DATE = LocalDate.now().minusYears(20);
    private static final String ALICE_ADDRESS = "Lisova 45";
    private static final String JOHN_PHONE_NUMBER = "+380985454523";
    private static final String JOHN_EMAIL = "john@gmail.com";
    private static final String JOHN_FIRST_NAME = "John";
    private static final String JOHN_LAST_NAME = "Deck";
    private static final LocalDate JOHN_BIRTH_DATE = LocalDate.now().minusYears(24);
    private static final String JOHN_ADDRESS = "Shevchenko 78";
    private static final String ALICE_ID = "/1";
    private static UserRequestDto aliceRequestDto;
    private static UserResponseDto aliceResponseDto;
    private static UserFindRequestDto userFindRequestDto;
    private static UserRequestDto johnRequestDto;
    private static UserResponseDto johnResponseDto;
    private static UserUpdateRequestDto aliceUpdateRequestDto;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        aliceRequestDto = new UserRequestDto().setAddress(ALICE_ADDRESS)
                .setEmail(ALICE_EMAIL)
                .setBirthDate(ALICE_BIRTH_DATE)
                .setFirstName(ALICE_FIRST_NAME)
                .setLastName(ALICE_LAST_NAME)
                .setPhoneNumber(ALICE_PHONE_NUMBER);
        johnRequestDto = new UserRequestDto().setAddress(JOHN_ADDRESS)
                .setEmail(JOHN_EMAIL)
                .setBirthDate(JOHN_BIRTH_DATE)
                .setFirstName(JOHN_FIRST_NAME)
                .setLastName(JOHN_LAST_NAME)
                .setPhoneNumber(JOHN_PHONE_NUMBER);
        johnResponseDto = new UserResponseDto().setAddress(JOHN_ADDRESS)
                .setEmail(JOHN_EMAIL)
                .setBirthDate(JOHN_BIRTH_DATE)
                .setFirstName(JOHN_FIRST_NAME)
                .setLastName(JOHN_LAST_NAME)
                .setPhoneNumber(JOHN_PHONE_NUMBER);
        aliceUpdateRequestDto = new UserUpdateRequestDto().setEmail(ALICE_NEW_EMAIL)
                .setFirstName(ALICE_NEW_FIRST_NAME)
                .setLastName(ALICE_NEW_LAST_NAME)
                .setBirthDate(LocalDate.now().minusYears(23))
                .setPhoneNumber(ALICE_NEW_PHONE_NUMBER);
        userFindRequestDto = new UserFindRequestDto().setFrom(LocalDate.now()
                        .minusYears(50)).setTo(LocalDate.now().minusYears(18));
    }

    @BeforeEach
    void setUp() {
        aliceResponseDto = new UserResponseDto().setAddress(ALICE_ADDRESS)
                .setEmail(ALICE_EMAIL)
                .setBirthDate(ALICE_BIRTH_DATE)
                .setFirstName(ALICE_FIRST_NAME)
                .setLastName(ALICE_LAST_NAME)
                .setPhoneNumber(ALICE_PHONE_NUMBER);
    }

    @Test
    @DisplayName("""
            Verify registerUser() method
            """)
    @Sql(scripts = BASE_PATH + REMOVE_ALICE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void registerUser_ValidUserInput_ReturnUserResponseDto() throws Exception {
        // given
        String jsonBody = objectMapper.writeValueAsString(aliceRequestDto);

        // when
        MvcResult mvcResult = mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)).andExpect(status().isCreated()).andReturn();

        // then
        UserResponseDto actual = objectMapper.readValue(mvcResult
                .getResponse().getContentAsString(), UserResponseDto.class);
        UserResponseDto expected = aliceResponseDto;
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, ID);
    }

    @Test
    @DisplayName("""
            Verify updateSomeUserInformation() method
            """)
    @Sql(scripts = BASE_PATH + ADD_ALICE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = BASE_PATH + REMOVE_ALISSON,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateSomeUserInformation_ValidInputs_ReturnUserResponseDto() throws Exception {
        // given
        String jsonBody = objectMapper.writeValueAsString(aliceUpdateRequestDto);

        // when
        MvcResult result = mockMvc.perform(patch(BASE_URL + ALICE_ID)
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        // then
        UserResponseDto actual = objectMapper.readValue(result
                .getResponse().getContentAsString(), UserResponseDto.class);
        UserResponseDto expected = aliceResponseDto.setEmail(ALICE_NEW_EMAIL)
                .setFirstName(ALICE_NEW_FIRST_NAME)
                .setLastName(ALICE_NEW_LAST_NAME);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(actual, expected, ID);
    }

    @Test
    @DisplayName("""
            Verify getAllUsersByBirthDateRange() method
            """)
    @Sql(scripts = BASE_PATH + ADD_ALICE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = BASE_PATH + REMOVE_ALICE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllUsersByBirthDateRange_ValidInputs_ReturnUserResponseDto() throws Exception {
        // given
        String jsonBody = objectMapper.writeValueAsString(userFindRequestDto);

        // when
        MvcResult result = mockMvc.perform(get(BASE_URL + DATE_RANGE)
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody)).andReturn();

        // then
        List<UserResponseDto> actual = objectMapper.readValue(result.getResponse()
                        .getContentAsString(),
                new TypeReference<List<UserResponseDto>>(){});
        List<UserResponseDto> expected = List.of(aliceResponseDto);
        assertEquals(1, actual.size());
        EqualsBuilder.reflectionEquals(expected, actual, ID);
    }

    @Test
    @DisplayName("""
            Verify updateAllUserInformation() method
            """)
    @Sql(scripts = BASE_PATH + ADD_ALICE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = BASE_PATH + REMOVE_JOHN,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateAllUserInformation_ValidInputs_ReturnUserResponseDto() throws Exception {
        // given
        String jsonBody = objectMapper.writeValueAsString(johnRequestDto);

        // when
        MvcResult mvcResult = mockMvc.perform(put(BASE_URL + ALICE_ID)
                .contentType(MediaType.APPLICATION_JSON).content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        // then
        UserResponseDto actual = objectMapper.readValue(mvcResult.getResponse()
                        .getContentAsString(),
                UserResponseDto.class);
        UserResponseDto expected = johnResponseDto;
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, ID);
    }
}
