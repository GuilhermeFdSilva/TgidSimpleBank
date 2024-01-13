package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;
    @Autowired
    private TransactionService transactionService;

    public Client createClient(Client client) {
        if (client.validateCpf()) {
            return repository.save(client);
        } else {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    public void deleteClient(Long id) {
        if (id != null && id > 0 && repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Cliente não encontrado");
        }
    }

    public List<Client> listClientsByCompany(Company company) {
        return repository.findByCompanyId(company.getId());
    }

    public Client validateLoginByCpf(Client client) {
        Client clientOnDB = repository.findByCpf(client.getCpf());

        if (clientOnDB == null || !clientOnDB.getPassword().equals(client.getPassword())) {
            throw new EntityNotFoundException("Dados do usuário incorretos");
        }

        return clientOnDB;
    }

    public Transaction newTransaction(Client author, Transaction.TransactionType type, Double value) {
        return transactionService.registerTransaction(author, type, value);
    }
}
