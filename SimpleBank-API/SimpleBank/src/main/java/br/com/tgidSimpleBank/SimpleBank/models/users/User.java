package br.com.tgidSimpleBank.SimpleBank.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String address;
    @Column(columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double balance;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
}
