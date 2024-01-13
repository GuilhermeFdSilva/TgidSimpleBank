package br.com.tgidSimpleBank.SimpleBank.models.plans.categories;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STANDARD")
public class StandardPlan extends Plan {
    public StandardPlan() {
        setId(2L);
        setDepositFee(0.015);
        setWithdrawalFee(0.025);
    }
}
