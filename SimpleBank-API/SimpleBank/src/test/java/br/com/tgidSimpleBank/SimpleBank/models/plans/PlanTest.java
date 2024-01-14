package br.com.tgidSimpleBank.SimpleBank.models.plans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PlanTest {
    private Plan plan;

    private final Long ID = 1L;
    private final double DEPOSIT_FEE = 1.0;
    private final double WITHDRAWAL_FEE = 2.0;

    @BeforeEach
    void setUp() {
        plan = new Plan();
        plan.setId(ID);
        plan.setDepositFee(DEPOSIT_FEE);
        plan.setWithdrawalFee(WITHDRAWAL_FEE);
    }

    @Test
    void testGetId() {
        assertEquals(ID, plan.getId());
    }

    @Test
    void testGetDepositFee() {
        assertEquals(DEPOSIT_FEE, plan.getDepositFee());
    }

    @Test
    void testGetWithdrawalFee() {
        assertEquals(WITHDRAWAL_FEE, plan.getWithdrawalFee());
    }

    @Test
    void testRegisterPlan() {
        assertEquals(Plan.getRegisteredPlans().size(), 3);

        Plan.registerPlan(plan);

        assertEquals(Plan.getRegisteredPlans().size(), 4);
        assertEquals(Plan.getRegisteredPlans().get(3), plan);
    }
}
