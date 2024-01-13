package br.com.tgidSimpleBank.SimpleBank.helpers.initializers;

import br.com.tgidSimpleBank.SimpleBank.models.plans.Plan;
import br.com.tgidSimpleBank.SimpleBank.models.plans.categories.BasicPlan;
import br.com.tgidSimpleBank.SimpleBank.models.plans.categories.PremiumPlan;
import br.com.tgidSimpleBank.SimpleBank.models.plans.categories.StandardPlan;
import br.com.tgidSimpleBank.SimpleBank.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlansInitializer implements CommandLineRunner {
    @Autowired
    private PlanRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Plan planRecorder = new Plan();

        BasicPlan basicPlan = new BasicPlan();
        StandardPlan standardPlan = new StandardPlan();
        PremiumPlan premiumPlan = new PremiumPlan();

        Plan.registerPlan(basicPlan);
        Plan.registerPlan(standardPlan);
        Plan.registerPlan(premiumPlan);

        List<Plan> planList = Plan.getRegisteredPlans();

        String response = "" +
                repository.saveAll(Plan.getRegisteredPlans());

        System.out.println(response);
    }
}
