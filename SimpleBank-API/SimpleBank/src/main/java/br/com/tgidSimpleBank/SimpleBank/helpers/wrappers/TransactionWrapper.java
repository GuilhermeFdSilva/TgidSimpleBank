package br.com.tgidSimpleBank.SimpleBank.helpers.wrappers;

import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionWrapper {
    private Client author;
    private Double value;
}
