package br.com.tgidSimpleBank.SimpleBank.models.plans.categories;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;

public class StandardPlan extends Plan {
    public StandardPlan() {
        setId(3L);
        setName("Standard");
        setDepositFee(0.015);
        setWithdrawalFee(0.025);
        registerPlan(this);
    }
}
