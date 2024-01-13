package br.com.tgidSimpleBank.SimpleBank.models.transactions;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private Double administrativeFee;
    @Column(nullable = false)
    private Double totalValue;
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


