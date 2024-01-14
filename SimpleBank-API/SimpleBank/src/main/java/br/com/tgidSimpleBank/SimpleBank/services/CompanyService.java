package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.helpers.exceptions.InsufficientBalanceException;
import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import br.com.tgidSimpleBank.SimpleBank.repositories.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;

    public Company createCompany(Company company) {
        if (company.validateCnpj()) {
            return repository.save(company);
        } else {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    }

    public void deleteCompany(Long id) {
        if (id != null && id > 0 && repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Empresa não encontrada");
        }
    }

    public Company validateLoginByCnpj(Company company) {
        Company companyOnDB = repository.findByCnpj(company.getCnpj());

        if (companyOnDB == null || !companyOnDB.getPassword().equals(company.getPassword())) {
            throw new EntityNotFoundException("Dados do usuário incorretos");
        }

        return companyOnDB;
    }

    protected void registerDeposit(Transaction transaction) {
        Company target = repository.findById(transaction.getTarget().getId()).orElseThrow(EntityNotFoundException::new);

        target.deposit(transaction.getTotalValue());
        repository.save(target);
    }

    protected void registerWithdrawal(Transaction transaction) {
        Company target = repository.findById(transaction.getTarget().getId()).orElseThrow(EntityNotFoundException::new);

        if (target.withdrawal(transaction.getTotalValue())) {
            repository.save(target);
        } else {
            throw new InsufficientBalanceException();
        }
    }
}
