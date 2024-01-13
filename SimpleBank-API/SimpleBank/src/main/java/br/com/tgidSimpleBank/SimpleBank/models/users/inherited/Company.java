package br.com.tgidSimpleBank.SimpleBank.models.users.inherited;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company extends User {
    @Column(nullable = false)
    private String cnpj;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double balance;
    @ManyToOne
    private Plan plan;

    public boolean validateCnpj() {
        return this.cnpj.replaceAll("\\D", "").length() == 14;
    }

    public void deposit(Double value) {
        this.balance+=value;
    }

    public boolean withdrawal(Double value) {
        if (this.balance < value) {
            return false;
        } else {
            this.balance-=value;
            return true;
        }
    }
}
