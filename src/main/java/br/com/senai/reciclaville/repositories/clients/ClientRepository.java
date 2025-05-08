package br.com.senai.reciclaville.repositories.clients;

import br.com.senai.reciclaville.models.entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Clients, Long> {
}
