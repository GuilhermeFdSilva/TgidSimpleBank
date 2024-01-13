package br.com.tgidSimpleBank.SimpleBank.repositories;

import br.com.tgidSimpleBank.SimpleBank.models.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
