package br.com.tgidSimpleBank.SimpleBank.models.users.inherited;

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
@Table(name = "clients")
public class Client extends User {
    @Column(nullable = false)
    private String cpf;
    @ManyToOne
    private Company company;

    public boolean validateCpf() {
        return this.cpf.replaceAll("\\D", "").length() == 11;
    }
}
