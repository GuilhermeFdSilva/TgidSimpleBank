package br.com.tgidSimpleBank.SimpleBank.repositories;

import br.com.tgidSimpleBank.SimpleBank.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
