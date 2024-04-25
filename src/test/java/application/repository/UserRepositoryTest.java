package application.repository;

import static org.junit.Assert.assertEquals;

import application.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    private static final Long ALICE_ID = 1L;
    private static final Integer FIFTY = 50;
    private static final Integer EIGHTEEN = 18;
    private static final String BASE_PATH = "classpath:database/users/";
    private static final String ADD_ALICE = "add_alice_to_users_table.sql";
    private static final String REMOVE_ALICE = "delete_alice_from_users_table.sql";
    private static final String ALICE_PHONE_NUMBER = "+380985454523";
    private static final String ALICE_EMAIL = "alice@gmail.com";
    private static final String ALICE_FIRST_NAME = "Alice";
    private static final String ALICE_LAST_NAME = "Johnson";
    private static final LocalDate ALICE_BIRTH_DATE = LocalDate.of(2002, 2, 2);
    private static final String ALICE_ADDRESS = "Lisova 45";
    private static User alice;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void beforeAll() {
        alice = new User().setAddress(ALICE_ADDRESS)
                .setEmail(ALICE_EMAIL)
                .setBirthDate(ALICE_BIRTH_DATE)
                .setFirstName(ALICE_FIRST_NAME)
                .setLastName(ALICE_LAST_NAME)
                .setPhoneNumber(ALICE_PHONE_NUMBER)
                .setId(ALICE_ID);
    }

    @Test
    @DisplayName("""
            Verify findUserByEmail() method
            """)
    @Sql(scripts = BASE_PATH + ADD_ALICE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = BASE_PATH + REMOVE_ALICE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findUserByEmail_ValidEmail_ReturnUser() {
        Optional<User> actual = userRepository.findUserByEmail(ALICE_EMAIL);
        User expected = alice;
        assertEquals(expected, actual.get());
    }

    @Test
    @DisplayName("""
            Verify getAllByBirthDayAfterAndBirthDayBefore() method
            """)
    @Sql(scripts = BASE_PATH + ADD_ALICE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = BASE_PATH + REMOVE_ALICE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllByBirthDayAfterAndBirthDayBefore_ValidInputs_ReturnOneUser() {
        List<User> actual = userRepository.getAllByBirthDateAfterAndBirthDateBefore(LocalDate.now()
                .minusYears(FIFTY), LocalDate.now().minusYears(EIGHTEEN));
        List<User> expected = List.of(alice);
        assertEquals(expected, actual);
    }
}
