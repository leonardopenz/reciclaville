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

    public StatementsResponseDTO newStatement(StatementsRequestDTO statementsRequestDTO){

        Clients client = clientRepository.findById(statementsRequestDTO.getClienteId())
                .orElseThrow(()-> new EntityNotFoundException("Cliente: "+ statementsRequestDTO.getClienteId()+" não encontrado!"));

        if(statementsRequestDTO.getDataInicial().isAfter(statementsRequestDTO.getDataFinal())){
            throw new IllegalArgumentException("Data inicial não pode ser maior que a Data final!");
        }

        Statements statement = new Statements();
        statement.setCliente(client.getNome());
        statement.setDataDeclaracao(LocalDate.now());
        statement.setDataInicial(statementsRequestDTO.getDataInicial());
        statement.setDataFinal(statementsRequestDTO.getDataFinal());

        List<Items> itemsOfStatements = statementsRequestDTO.getItensDeclaracao().stream()
                .map(item ->{
                    if(item.getToneladasDeclaradas() == null || item.getToneladasDeclaradas() <= 0){
                        throw new IllegalArgumentException("Toneladas declaradas devem ser maior que 0(zero)");
                    }

                Materials material = materialRepository.findById(item.getMaterials().getId())
                        .orElseThrow(()-> new EntityNotFoundException("Material não encontrado: "+ item.getMaterials().getId()));
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

        Statements saved = statementsRepository.save(statement);

        return new StatementsResponseDTO(
                saved.getId(),
                saved.getCliente(),
                saved.getDataDeclaracao(),
                saved.getDataInicial(),
                saved.getDataFinal(),
                saved.getTotalMateriais(),
                saved.getTotalCompensacao(),
                saved.getItensDeclaracao()
        );
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
