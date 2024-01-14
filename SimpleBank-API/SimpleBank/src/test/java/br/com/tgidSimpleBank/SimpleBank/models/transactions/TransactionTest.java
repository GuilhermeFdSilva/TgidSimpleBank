package br.com.tgidSimpleBank.SimpleBank.models.transactions;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionTest {
    private Transaction transaction;

    private final Long ID = 1L;
    private final double VALUE = 50.0;
    private final double FEE = 0.01;
    private final double TOTAL_VALUE = 45.5;
    private final Company TARGET = new Company();
    private final User AUTHOR = new Client();
    private final Transaction.TransactionType TYPE = Transaction.TransactionType.DEPOSIT;
    private Date now;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(ID);
        transaction.setValue(VALUE);
        transaction.setAdministrativeFee(FEE);
        transaction.setTotalValue(TOTAL_VALUE);
        transaction.setTarget(TARGET);
        transaction.setAuthor(AUTHOR);
        transaction.setType(TYPE);

        java.util.Date javaDateNow = Calendar.getInstance().getTime();
        now = new Date(javaDateNow.getTime());
        transaction.setDate(now);
    }

    @Test
    void testGetId() {
        assertEquals(ID, transaction.getId());
    }

    @Test
    void testGetValue() {
        assertEquals(VALUE, transaction.getValue());
    }

    @Test
    void testGetAdministrativeFee() {
        assertEquals(FEE, transaction.getAdministrativeFee());
    }

    @Test
    void testGetTotalValue() {
        assertEquals(TOTAL_VALUE, transaction.getTotalValue());
    }

    @Test
    void testGetTarget() {
        assertEquals(TARGET, transaction.getTarget());
    }

    @Test
    void testGetAuthor() {
        assertEquals(AUTHOR, transaction.getAuthor());
    }

    @Test
    void testGetType() {
        assertEquals(TYPE, transaction.getType());
    }

    @Test
    void testGetDate() {
        assertEquals(now, transaction.getDate());
    }

    @Test
    void testBuilder() {
        Transaction builderTransaction = Transaction
                .builder()
                .id(ID)
                .value(VALUE)
                .administrativeFee(FEE)
                .totalValue(TOTAL_VALUE)
                .target(TARGET)
                .author(AUTHOR)
                .type(TYPE)
                .build();

        assertAll(
                () -> assertEquals(ID, builderTransaction.getId()),
                () -> assertEquals(VALUE, builderTransaction.getValue()),
                () -> assertEquals(FEE, builderTransaction.getAdministrativeFee()),
                () -> assertEquals(TOTAL_VALUE, builderTransaction.getTotalValue()),
                () -> assertEquals(TARGET, builderTransaction.getTarget()),
                () -> assertEquals(AUTHOR, builderTransaction.getAuthor()),
                () -> assertEquals(TYPE, builderTransaction.getType())
        );
    }
}
