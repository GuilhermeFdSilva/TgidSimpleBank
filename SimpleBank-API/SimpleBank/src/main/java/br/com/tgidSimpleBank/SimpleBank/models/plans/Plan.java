package br.com.tgidSimpleBank.SimpleBank.models.plans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plans")
public class Plan {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
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
