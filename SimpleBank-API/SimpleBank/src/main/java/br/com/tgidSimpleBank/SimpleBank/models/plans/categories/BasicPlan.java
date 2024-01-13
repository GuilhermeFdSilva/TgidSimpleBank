package br.com.tgidSimpleBank.SimpleBank.models.plans.categories;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;

public class BasicPlan extends Plan {
    public BasicPlan() {
        setId(1L);
        setName("Basic");
        setDepositFee(0.01);
        setWithdrawalFee(0.02);
        registerPlan(this);
    }
}
