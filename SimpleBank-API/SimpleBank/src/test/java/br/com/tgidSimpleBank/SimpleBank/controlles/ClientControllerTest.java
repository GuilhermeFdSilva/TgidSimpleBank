package br.com.tgidSimpleBank.SimpleBank.controlles;

import br.com.tgidSimpleBank.SimpleBank.controllers.ClientController;
import br.com.tgidSimpleBank.SimpleBank.helpers.exceptions.InsufficientBalanceException;
import br.com.tgidSimpleBank.SimpleBank.helpers.wrappers.TransactionWrapper;
import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.services.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
    @Mock
    private ClientService service;
    private final Client CLIENT = new Client();
    private final Company COMPANY = new Company();

    @InjectMocks
    private ClientController controller;

    @Test
    void testListClients() {
        List<Client> clients = new ArrayList<>(List.of(new Client(), new Client()));

        when(service.listClientsByCompany(COMPANY)).thenReturn(clients);

        ResponseEntity<?> response = controller.listClients(COMPANY);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());

        verify(service, times(1)).listClientsByCompany(COMPANY);
    }

    @Test
    void testEmptyListClients() {
        when(service.listClientsByCompany(COMPANY)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.listClients(COMPANY);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testLoginClient() {
        when(service.validateLoginByCpf(CLIENT)).thenReturn(CLIENT);

        ResponseEntity<?> response = controller.loginClient(CLIENT);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CLIENT, response.getBody());

        verify(service, times(1)).validateLoginByCpf(CLIENT);
    }

    @Test
    void testInvalidLoginClient() {
        when(service.validateLoginByCpf(CLIENT)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.loginClient(CLIENT);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDoATransaction() {
        Transaction transaction = new Transaction();
        TransactionWrapper wrapper = new TransactionWrapper();

        wrapper.setAuthor(CLIENT);
        wrapper.setValue(100.0);

        when(service.newTransaction(CLIENT, Transaction.TransactionType.DEPOSIT, 100.0)).thenReturn(transaction);

        ResponseEntity<?> response = controller.doATransaction("deposit", wrapper);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transaction, response.getBody());

        verify(service, times(1)).newTransaction(CLIENT, Transaction.TransactionType.DEPOSIT, wrapper.getValue());
    }

    @Test
    void testIllegalArgumentTransaction() {
        TransactionWrapper wrapper = new TransactionWrapper();

        wrapper.setAuthor(CLIENT);
        wrapper.setValue(100.0);

        when(service.newTransaction(CLIENT, Transaction.TransactionType.DEPOSIT, 100.0)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.doATransaction("deposit", wrapper);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testInsufficientBalanceTransaction() {
        TransactionWrapper wrapper = new TransactionWrapper();

        wrapper.setAuthor(CLIENT);
        wrapper.setValue(100.0);

        when(service.newTransaction(CLIENT, Transaction.TransactionType.DEPOSIT, 100.0)).thenThrow(InsufficientBalanceException.class);

        ResponseEntity<?> response = controller.doATransaction("deposit", wrapper);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testEntityNotFoundTransaction() {
        TransactionWrapper wrapper = new TransactionWrapper();

        wrapper.setAuthor(CLIENT);
        wrapper.setValue(100.0);

        when(service.newTransaction(CLIENT, Transaction.TransactionType.DEPOSIT, 100.0)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.doATransaction("deposit", wrapper);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateClient() {
        when(service.createClient(CLIENT)).thenReturn(CLIENT);

        ResponseEntity<?> response = controller.createClient(CLIENT);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CLIENT, response.getBody());

        verify(service, times(1)).createClient(CLIENT);
    }

    @Test
    void testCreateInvalidClient() {
        when(service.createClient(CLIENT)).thenThrow(IllegalArgumentException.class);

        ResponseEntity<?> response = controller.createClient(CLIENT);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateClient() {
        when(service.updateClient(CLIENT)).thenReturn(CLIENT);

        ResponseEntity<?> response = controller.updateClient(CLIENT);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CLIENT, response.getBody());

        verify(service, times(1)).updateClient(CLIENT);
    }

    @Test
    void testUpdateInvalidClient() {
        when(service.updateClient(CLIENT)).thenThrow(EntityNotFoundException.class);

        ResponseEntity<?> response = controller.updateClient(CLIENT);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteClient() {
        ResponseEntity<?> response = controller.deleteClient(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(service, times(1)).deleteClient(1L);
    }

    @Test
    void testDeleteInvalidClient() {
        doThrow(EntityNotFoundException.class).when(service).deleteClient(1L);

        ResponseEntity<?> response = controller.deleteClient(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}
