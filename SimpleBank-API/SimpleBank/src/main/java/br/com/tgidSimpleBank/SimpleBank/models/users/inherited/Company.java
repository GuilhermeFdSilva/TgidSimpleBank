package br.com.tgidSimpleBank.SimpleBank.models.users.inherited;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
