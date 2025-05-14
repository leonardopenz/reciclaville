package br.com.senai.reciclaville.repositories.items;

import br.com.senai.reciclaville.models.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {
}
