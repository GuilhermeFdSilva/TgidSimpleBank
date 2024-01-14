package br.com.tgidSimpleBank.SimpleBank.helpers.wrappers;

import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionWrapperTest {
    private TransactionWrapper wrapper;

    private final Client AUTHOR = new Client();
    private final double VALUE = 100.0;

    @BeforeEach
    void setUp() {
        wrapper = new TransactionWrapper();
        wrapper.setAuthor(AUTHOR);
        wrapper.setValue(VALUE);
    }

    @Test
    void testGetAuthor() {
        assertEquals(AUTHOR, wrapper.getAuthor());
    }

    @Test
    void testGetValue() {
        assertEquals(VALUE, wrapper.getValue());
    }
}
