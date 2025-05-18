package br.com.senai.reciclaville.services.statements;

import br.com.senai.reciclaville.models.DTOS.items.ItemsRequestDTO;
import br.com.senai.reciclaville.models.DTOS.statements.StatementsRequestDTO;
import br.com.senai.reciclaville.models.DTOS.statements.StatementsResponseDTO;
import br.com.senai.reciclaville.models.entities.Clients;
import br.com.senai.reciclaville.models.entities.Items;
import br.com.senai.reciclaville.models.entities.Materials;
import br.com.senai.reciclaville.models.entities.Statements;
import br.com.senai.reciclaville.repositories.clients.ClientRepository;
import br.com.senai.reciclaville.repositories.items.ItemsRepository;
import br.com.senai.reciclaville.repositories.materials.MaterialRepository;
import br.com.senai.reciclaville.repositories.statements.StatementsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatementsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private StatementsRepository statementsRepository;

    public Statements newStatement(StatementsRequestDTO statementsRequestDTO) {

        Clients client = clientRepository.findById(statementsRequestDTO.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente: " + statementsRequestDTO.getClienteId() + " não encontrado!"));

        if (statementsRequestDTO.getDataInicial().isAfter(statementsRequestDTO.getDataFinal())) {
            throw new IllegalArgumentException("Data inicial não pode ser maior que a Data final!");
        }

        Statements statement = new Statements();
        statement.setCliente(client);
        statement.setDataDeclaracao(LocalDate.now());
        statement.setDataInicial(statementsRequestDTO.getDataInicial());
        statement.setDataFinal(statementsRequestDTO.getDataFinal());
        statement.setTotalCompensacao(0.0);
        statement.setTotalMateriais(0.0);

        // Salva o statement antes para gerar o ID (se necessário)
        statement = statementsRepository.save(statement);

        List<Items> itemsOfStatements = new ArrayList<>();
        double totalMateriais = 0;
        double totalCompensacao = 0;

        for (ItemsRequestDTO itemDTO : statementsRequestDTO.getItensDeclaracao()) {
            if (itemDTO.getToneladasDeclaradas() == null || itemDTO.getToneladasDeclaradas() <= 0) {
                throw new IllegalArgumentException("Toneladas declaradas devem ser maior que 0 (zero)");
            }

            Materials material = materialRepository.findById(itemDTO.getMaterial_id())
                    .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + itemDTO.getMaterial_id()));

            double toneladasCompensadas = itemDTO.getToneladasDeclaradas() * material.getPercentualCompensacao() / 100.0;

            Items item = new Items();
            item.setMaterials(material);
            item.setToneladasDeclaradas(itemDTO.getToneladasDeclaradas());
            item.setToneladasCompensadas(toneladasCompensadas);
            item.setPercentualCompensacao(material.getPercentualCompensacao());
            item.setStatement(statement);

            itemsOfStatements.add(item);
            totalMateriais += item.getToneladasDeclaradas();
            totalCompensacao += toneladasCompensadas;
        }

        itemsRepository.saveAll(itemsOfStatements);

        statement.setTotalMateriais(totalMateriais);
        statement.setTotalCompensacao(totalCompensacao);
        statement.setItensDeclaracao(itemsOfStatements);

        return statementsRepository.save(statement);
    }


    public List<Statements> findAllStatements(){
        return statementsRepository.findAll();
    }

    public Optional<Statements> findStatementById(Long id){
        return statementsRepository.findById(id);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteStatement(Long id) {
        // Deleta primeiro os registros da join table (statements_items)
        entityManager.createNativeQuery("""
        DELETE FROM declaracoes_itens_declaracao WHERE statements_id = :id
    """).setParameter("id", id).executeUpdate();

        // Depois deleta o próprio statement
        statementsRepository.deleteById(id);
    }

}
