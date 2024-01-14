package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.helpers.exceptions.InsufficientBalanceException;
import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.repositories.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository repository;
    @Mock
    private final Company COMPANY = new Company();
    @Mock
    private final Transaction TRANSACTION = new Transaction();

    @InjectMocks
    private CompanyService service;

    @BeforeEach
    void setUp() {
        COMPANY.setCnpj("12.123.123/1234-12");
    }

    @Test
    void testCreateValidCompany() {
        when(COMPANY.validateCnpj()).thenReturn(true);
        when(repository.save(COMPANY)).thenReturn(COMPANY);

        Company company = service.createCompany(COMPANY);
        assertEquals(COMPANY, company);
        verify(repository, times(1)).save(COMPANY);
    }

    @Test
    void testCreateInvalidCompany() {
        when(COMPANY.validateCnpj()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.createCompany(COMPANY));
        verify(repository, never()).save(any());
    }

    @Test
    void testUpdateValidCompany() {
        when(COMPANY.getId()).thenReturn(1L);
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(COMPANY)).thenReturn(COMPANY);

        Company company = service.updateCompany(COMPANY);
        assertEquals(COMPANY, company);
        verify(repository, times(1)).save(COMPANY);
    }

    @Test
    void testUpdateInvalidCompany() {
        assertThrows(EntityNotFoundException.class, () -> service.updateCompany(COMPANY));
    }

    @Test
    void testDeleteValidCompany() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteCompany(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInvalidCompany() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deleteCompany(1L));

        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void testValidLogin() {
        when(repository.findByCnpj(COMPANY.getCnpj())).thenReturn(COMPANY);
        when(COMPANY.getPassword()).thenReturn("123");

        Company company = service.validateLoginByCnpj(COMPANY);

        assertEquals(COMPANY, company);
        verify(repository, times(1)).findByCnpj(COMPANY.getCnpj());
    }

    @Test
    void testInvalidLogin() {
        when(repository.findByCnpj(COMPANY.getCnpj())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> service.validateLoginByCnpj(COMPANY));
    }

    @Test
    void testRegisterDeposit() {
        when(repository.findById(COMPANY.getId())).thenReturn(Optional.of(COMPANY));
        when(TRANSACTION.getTarget()).thenReturn(COMPANY);

        service.registerDeposit(TRANSACTION);

        verify(COMPANY, times(1)).deposit(anyDouble());
        verify(repository, times(1)).save(COMPANY);
    }

    @Test
    void testRegisterValidWithdrawal() {
        when(repository.findById(COMPANY.getId())).thenReturn(Optional.of(COMPANY));
        when(TRANSACTION.getTarget()).thenReturn(COMPANY);
        when(COMPANY.withdrawal(anyDouble())).thenReturn(true);

        service.registerWithdrawal(TRANSACTION);

        verify(repository, times(1)).save(COMPANY);
    }

    @Test
    void testRegisterInvalidWithdrawal() {
        when(repository.findById(COMPANY.getId())).thenReturn(Optional.of(COMPANY));
        when(TRANSACTION.getTarget()).thenReturn(COMPANY);
        when(COMPANY.withdrawal(anyDouble())).thenReturn(false);

        assertThrows(InsufficientBalanceException.class, () -> service.registerWithdrawal(TRANSACTION));
        verify(repository, never()).save(COMPANY);
    }
}
