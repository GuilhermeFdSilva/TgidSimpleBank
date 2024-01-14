package br.com.tgidSimpleBank.SimpleBank.controllers;

import br.com.tgidSimpleBank.SimpleBank.helpers.exceptions.InsufficientBalanceException;
import br.com.tgidSimpleBank.SimpleBank.helpers.wrappers.TransactionWrapper;
import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.services.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping("/list")
    public ResponseEntity<?> listClients(@RequestBody Company company) {
        try {
            List<Client> clients = service.listClientsByCompany(company);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> loginClient(@RequestBody Client client) {
        try {
            Client clientOnDB = service.validateLoginByCpf(client);
            return new ResponseEntity<>(clientOnDB, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{type}")
    public ResponseEntity<?> doATransaction(@PathVariable String type, @RequestBody TransactionWrapper wrapper) {
        try {
            Transaction.TransactionType transactionType = Transaction.TransactionType.valueOf(type.toUpperCase());
            Transaction transaction = service.newTransaction(wrapper.getAuthor(), transactionType, wrapper.getValue());
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (IllegalArgumentException | InsufficientBalanceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        try {
            Client clientCreated = service.createClient(client);
            return new ResponseEntity<>(clientCreated, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        try {
            Client upToDateClient = service.updateClient(client);
            return new ResponseEntity<>(upToDateClient, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            service.deleteClient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
