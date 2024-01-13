package br.com.tgidSimpleBank.SimpleBank.repositories;

import br.com.tgidSimpleBank.SimpleBank.models.users.inherited.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c WHERE c.cnpj = :cnpj")
    public Company findByCnpj(String cnpj);
}
