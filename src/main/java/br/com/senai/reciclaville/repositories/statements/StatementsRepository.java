package br.com.senai.reciclaville.repositories.statements;

import br.com.senai.reciclaville.models.entities.Statements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementsRepository extends JpaRepository<Statements, Long> {
}
