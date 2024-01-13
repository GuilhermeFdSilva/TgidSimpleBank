package br.com.tgidSimpleBank.SimpleBank.models.transactions;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double value;
    @ManyToOne
    private Company target;
    @ManyToOne
    private User author;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false)
    private TransactionType type;
    @Column(nullable = false)
    private Date date;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }
}


