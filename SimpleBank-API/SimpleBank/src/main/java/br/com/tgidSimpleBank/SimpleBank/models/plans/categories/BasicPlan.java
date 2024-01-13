package br.com.tgidSimpleBank.SimpleBank.models.plans.categories;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BASIC")
public class BasicPlan extends Plan {
    public BasicPlan() {
        setId(1L);
        setDepositFee(0.01);
        setWithdrawalFee(0.02);
    }
}
