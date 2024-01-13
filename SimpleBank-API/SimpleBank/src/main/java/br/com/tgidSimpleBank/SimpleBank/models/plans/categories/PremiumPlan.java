package br.com.tgidSimpleBank.SimpleBank.models.plans.categories;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;

public class PremiumPlan extends Plan {
    public PremiumPlan() {
        setId(2L);
        setName("Premium");
        setDepositFee(0.02);
        setWithdrawalFee(0.03);
        registerPlan(this);
    }
}
