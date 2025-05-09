package br.com.senai.reciclaville.repositories.materials;

import br.com.senai.reciclaville.models.entities.Materials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  MaterialRepository extends JpaRepository<Materials, Long> {
}
