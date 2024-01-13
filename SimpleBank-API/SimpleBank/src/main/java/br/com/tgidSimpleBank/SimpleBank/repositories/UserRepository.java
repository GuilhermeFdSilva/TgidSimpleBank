package br.com.tgidSimpleBank.SimpleBank.repositories;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(String email);
}
