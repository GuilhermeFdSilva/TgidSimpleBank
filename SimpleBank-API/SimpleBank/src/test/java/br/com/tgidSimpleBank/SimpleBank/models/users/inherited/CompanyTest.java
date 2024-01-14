package br.com.tgidSimpleBank.SimpleBank.models.users.inherited;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyTest {
    private Company company;

    private final String CNPJ = "12.123.123/1234-12";
    private final Plan PLAN = new Plan();

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setCnpj(CNPJ);
        company.setPlan(PLAN);
    }

    @Test
    void testGetCnpj() {
        assertEquals(CNPJ, company.getCnpj());
    }

    @Test
    void testGetPlan() {
        assertEquals(PLAN, company.getPlan());
    }

    @Test
    void testInitialBalance() {
        assertEquals(0.0, company.getBalance());
    }

    @Test
    void testSetAndGetBalance() {
        assertEquals(0.0, company.getBalance());
        company.setBalance(2.0);
        assertEquals(2.0, company.getBalance());
    }

    @Test
    void testValidCnpj() {
        assertTrue(company.validateCnpj());
    }

    @Test
    void testInvalidCnpj() {
        company.setCnpj("ab.abc.abc/abcd-ab");
        assertFalse(company.validateCnpj());
    }

    @Test
    void testDeposit() {
        assertEquals(0.0, company.getBalance());
        company.deposit(50.0);
        assertEquals(50.0, company.getBalance());
    }

    @Test
    void testWithdrawal() {
        assertFalse(company.withdrawal(50.0));
        assertEquals(0.0, company.getBalance());

        company.setBalance(100.0);
        assertTrue(company.withdrawal(50.0));
        assertEquals(50.0, company.getBalance());
    }
}
