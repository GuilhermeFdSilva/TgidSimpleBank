package br.com.tgidSimpleBank.SimpleBank.services;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import br.com.tgidSimpleBank.SimpleBank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private CompanyService companyService;

    protected Transaction registerTransaction(Client author, Transaction.TransactionType type, Double value) {
        Transaction transaction = buildTransaction(author, type, value);

        switch (type) {
            case DEPOSIT:
                companyService.registerDeposit(transaction);
                repository.save(transaction);
                return transaction;
            case WITHDRAWAL:
                companyService.registerWithdrawal(transaction);
                repository.save(transaction);
                return transaction;
            default:
                throw new IllegalArgumentException("Transação inválida");
        }
    }

    protected Transaction buildTransaction(Client author, Transaction.TransactionType type, Double value) {
        Plan plan = author.getCompany().getPlan();

        java.util.Date javaDateNow = Calendar.getInstance().getTime();
        Date now = new Date(javaDateNow.getTime());

        double fee = 0.0;
        double totalValue = 0.0;

        switch (type) {
            case DEPOSIT:
                fee = value * plan.getDepositFee();
                totalValue = value - fee;
                break;
            case WITHDRAWAL:
                fee = value * plan.getWithdrawalFee();
                totalValue = value + fee;
                break;
            default:
                throw new IllegalArgumentException("Transação inválida");
        }

        return Transaction.builder()
                .value(value)
                .administrativeFee(fee)
                .totalValue(totalValue)
                .target(author.getCompany())
                .author(author)
                .type(type)
                .date(now)
                .build();
    }
}
