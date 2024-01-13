package br.com.tgidSimpleBank.SimpleBank.repositories;

import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.cpf = :cpf")
    public Client findByCpf(String cpf);
    @Query("SELECT c FROM Client c WHERE c.company.id = :companyId")
    public List<Client> findByCompanyId(Long companyId);
}
