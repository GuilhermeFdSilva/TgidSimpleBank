package br.com.tgidSimpleBank.SimpleBank.models.users.inherited;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class ClientTest {
    private Client client;

    private final String CPF = "123.456.789-00";
    private final Company COMPANY = new Company();

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setCpf(CPF);
        client.setCompany(COMPANY);
    }

    @Test
    void testGetCpf() {
        assertEquals(CPF, client.getCpf());
    }

    @Test
    void testGetCompany() {
        assertEquals(COMPANY, client.getCompany());
    }

    @Test
    void testValidCpf() {
        assertTrue(client.validateCpf());
    }

    @Test
    void testInvalidCpf() {
        client.setCpf("abc.abc.abc-ab");
        assertFalse(client.validateCpf());
    }
}
