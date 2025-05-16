package br.com.senai.reciclaville.services.statements;

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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Statements newStatement(Statements statementInput) {

        Clients client = clientRepository.findById(statementInput.getCliente().getId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente: " + statementInput.getCliente() + " não encontrado!"));

        if (statementInput.getDataInicial().isAfter(statementInput.getDataFinal())) {
            throw new IllegalArgumentException("Data inicial não pode ser maior que a Data final!");
        }

        Statements statement = new Statements();
        statement.setCliente(client);
        statement.setDataDeclaracao(LocalDate.now());
        statement.setDataInicial(statementInput.getDataInicial());
        statement.setDataFinal(statementInput.getDataFinal());

        List<Items> itemsOfStatements = statementInput.getItensDeclaracao().stream().map(item -> {
            if (item.getToneladasDeclaradas() == null || item.getToneladasDeclaradas() <= 0) {
                throw new IllegalArgumentException("Toneladas declaradas devem ser maior que 0 (zero)");
            }

            Materials material = materialRepository.findById(item.getMaterials().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + item.getMaterials().getId()));

            item.setMaterials(material);
            item.setPercentualCompensacao(material.getPercentualCompensacao());
            item.setToneladasCompensadas(item.getToneladasDeclaradas() * material.getPercentualCompensacao() / 100);
            item.setStatement(statement);

            return item;
        }).toList();

        itemsRepository.saveAll(itemsOfStatements);

        double totalMateriais = itemsOfStatements.stream().mapToDouble(Items::getToneladasDeclaradas).sum();
        double totalCompensacao = itemsOfStatements.stream().mapToDouble(Items::getToneladasCompensadas).sum();

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

    public void deleteStatement(Long id){
        statementsRepository.deleteById(id);
    }
}
