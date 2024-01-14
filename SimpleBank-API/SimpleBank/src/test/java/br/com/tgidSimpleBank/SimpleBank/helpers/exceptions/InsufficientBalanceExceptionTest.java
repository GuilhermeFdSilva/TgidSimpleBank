package br.com.tgidSimpleBank.SimpleBank.helpers.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InsufficientBalanceExceptionTest {
    private InsufficientBalanceException exception;

    @BeforeEach
    void setUp() {
        exception = new InsufficientBalanceException();
    }

    @Test
    void testMessage() {
        assertEquals("Saldo insuficiente", exception.getMessage());
    }
}
