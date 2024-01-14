package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;
    @Mock
    private CompanyService companyService;
    private final Client AUTHOR = new Client();
    private final double VALUE = 100.0;

    @InjectMocks
    private TransactionService service;

    @BeforeEach
    void setUp() {
        Company company = new Company();
        Plan plan = new Plan();
        plan.setDepositFee(0.01);
        plan.setWithdrawalFee(0.02);
        company.setPlan(plan);

        AUTHOR.setCompany(company);
    }

    @Test
    void testValidRegisterDeposit() {
        Transaction.TransactionType type = Transaction.TransactionType.DEPOSIT;

        Transaction responseTransaction = service.registerTransaction(AUTHOR, type, VALUE);

        assertEquals(AUTHOR, responseTransaction.getAuthor());
        assertEquals(type, responseTransaction.getType());
        assertEquals(VALUE, responseTransaction.getValue());
        verify(companyService, times(1)).registerDeposit(responseTransaction);
        verify(repository, times(1)).save(responseTransaction);
    }

    @Test
    void testValidRegisterWithdrawal() {
        Transaction.TransactionType type = Transaction.TransactionType.WITHDRAWAL;

        Transaction responseTransaction = service.registerTransaction(AUTHOR, type, VALUE);

        assertEquals(AUTHOR, responseTransaction.getAuthor());
        assertEquals(type, responseTransaction.getType());
        assertEquals(VALUE, responseTransaction.getValue());
        verify(companyService, times(1)).registerWithdrawal(responseTransaction);
        verify(repository, times(1)).save(responseTransaction);
    }

    @Test
    void testInvalidTypeRegister() {
        Transaction.TransactionType type = Transaction.TransactionType.INVALID;

        assertThrows(IllegalArgumentException.class, () -> service.registerTransaction(AUTHOR, type, VALUE));
        verify(companyService, never()).registerDeposit(any());
        verify(repository, never()).save(any());
    }
}
