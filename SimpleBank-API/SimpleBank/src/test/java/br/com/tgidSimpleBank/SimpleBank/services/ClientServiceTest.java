package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository repository;
    @Mock
    private TransactionService transactionService;
    @Mock
    private final Client CLIENT = new Client();
    private final Company COMPANY = new Company();
    private final Transaction.TransactionType TYPE = Transaction.TransactionType.DEPOSIT;
    private final double VALUE = 100.0;

    @InjectMocks
    private ClientService service;

    @BeforeEach
    void setUp() {
        CLIENT.setCpf("123.123.123-12");
        COMPANY.setId(1L);
    }

    @Test
    void testCreateValidClient() {
        when(CLIENT.validateCpf()).thenReturn(true);
        when(repository.save(CLIENT)).thenReturn(CLIENT);

        Client clientCreated = service.createClient(CLIENT);
        assertEquals(CLIENT, clientCreated);
        verify(repository, times(1)).save(CLIENT);
    }

    @Test
    void testCreateInvalidClient() {
        when(CLIENT.validateCpf()).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.createClient(CLIENT));
        verify(repository, never()).save(any());
    }

    @Test
    void testUpdateValidClient() {
        when(CLIENT.getId()).thenReturn(1L);
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(CLIENT)).thenReturn(CLIENT);

        Client client = service.updateClient(CLIENT);
        assertEquals(CLIENT, client);
        verify(repository, times(1)).save(CLIENT);
    }

    @Test
    void testUpdateInvalidClient() {
        assertThrows(EntityNotFoundException.class, () -> service.updateClient(CLIENT));
    }

    @Test
    void testDeleteValidClient() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteClient(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteInvalidClient() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deleteClient(1L));

        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void testListClientsByCompany() {
        List<Client> clients = new ArrayList<>();
        List<Client> clientsFiltered;

        for (long i = 0; i < 5; i++) {
            Company company = new Company();
            company.setId(i);

            Client client = new Client();
            client.setCompany(company);

            clients.add(client);
        }
        clientsFiltered = clients.stream().filter((client -> client.getCompany().getId() == 1L)).toList();

        when(repository.findByCompanyId(1L)).thenReturn(clientsFiltered);

        List<Client> response = service.listClientsByCompany(COMPANY);

        assertIterableEquals(response, clientsFiltered);
        verify(repository, times(1)).findByCompanyId(1L);
    }

    @Test
    void testNullListOfClients() {
        when(repository.findByCompanyId(1L)).thenReturn(new ArrayList<>());

        assertThrows(EntityNotFoundException.class, ()-> service.listClientsByCompany(COMPANY));
    }

    @Test
    void testValidLogin() {
        when(repository.findByCpf(CLIENT.getCpf())).thenReturn(CLIENT);
        when(CLIENT.getPassword()).thenReturn("123");

        Client client = service.validateLoginByCpf(CLIENT);

        assertEquals(CLIENT, client);
        verify(repository, times(1)).findByCpf(CLIENT.getCpf());
    }

    @Test
    void testInvalidLogin() {
        when(repository.findByCpf(CLIENT.getCpf())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> service.validateLoginByCpf(CLIENT));
    }

    @Test
    void testDoNewTransaction() {
        service.newTransaction(CLIENT, TYPE, VALUE);

        verify(transactionService, times(1)).registerTransaction(CLIENT, TYPE, VALUE);
    }
}
