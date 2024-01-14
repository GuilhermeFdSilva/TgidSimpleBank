package br.com.tgidSimpleBank.SimpleBank.models.users;

import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {
    private User user;

    private final String NAME = "Tgid";
    private final String EMAIL = "email@email.com";
    private final String PASSWORD = "password123";
    private Date now;

    @BeforeEach
    void setUp() {
        user = new Client();
        user.setId(1L);
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        java.util.Date javaDateNow = Calendar.getInstance().getTime();
        now = new Date(javaDateNow.getTime());
        user.setCreatedAt(now);
    }

    @Test
    void testGetId() {
        assertEquals(1L, user.getId());
    }

    @Test
    void testGetName() {
        assertEquals(NAME, user.getName());
    }

    @Test
    void testGetEmail() {
        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    void testGetCreatedAt() {
        java.util.Date javaDateNow = Calendar.getInstance().getTime();
        assertEquals(now, user.getCreatedAt());
    }
}
