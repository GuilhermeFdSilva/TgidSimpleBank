package br.com.tgidSimpleBank.SimpleBank.models.plans.categories;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PREMIUM")
public class PremiumPlan extends Plan {
    public PremiumPlan() {
        setId(3L);
        setDepositFee(0.02);
        setWithdrawalFee(0.03);
    }
}
