package br.com.tgidSimpleBank.SimpleBank.models.plans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "plans")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "name")
public class Plan {
    @Id
    private Long id;
    @Column(nullable = false)
    private Double withdrawalFee;
    @Column(nullable = false)
    private Double depositFee;

    @Getter
    @Transient
    private static ArrayList<Plan> registeredPlans = new ArrayList<>();

    public static void registerPlan(Plan plan) {
        registeredPlans.add(plan);
    }
}
