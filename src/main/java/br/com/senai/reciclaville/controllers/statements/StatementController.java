package br.com.senai.reciclaville.controllers.statements;

import br.com.senai.reciclaville.models.DTOS.statements.StatementsRequestDTO;
import br.com.senai.reciclaville.models.DTOS.statements.StatementsResponseDTO;
import br.com.senai.reciclaville.models.entities.Statements;
import br.com.senai.reciclaville.services.statements.StatementsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/declaracoes")
public class StatementController {

    @Autowired
    private StatementsService statementsService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<StatementsResponseDTO> create(@RequestBody @Valid StatementsRequestDTO requestDTO) throws Exception{
        //Statements statement = modelMapper.map(requestDTO, Statements.class);
        Statements createdStatement = statementsService.newStatement(requestDTO);
        StatementsResponseDTO createdStatementDTO = modelMapper.map(createdStatement, StatementsResponseDTO.class);

        return ResponseEntity.ok(createdStatementDTO);
    }

    @GetMapping
    public ResponseEntity<List<StatementsResponseDTO>> findAllStatements(){
        List<StatementsResponseDTO> statements = this.statementsService.findAllStatements().stream()
                .map(statements1 -> modelMapper.map(statements1, StatementsResponseDTO.class))
                .collect(Collectors.toList());

        return statements.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(statements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatementsResponseDTO> findStatementById(@PathVariable Long id){
        Optional<Statements> statement = this.statementsService.findStatementById(id);

        return statement.map(statements -> {
            StatementsResponseDTO responseDTO = modelMapper.map(statements, StatementsResponseDTO.class);
            return ResponseEntity.ok(responseDTO);
        })
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatement(@PathVariable Long id){
        this.statementsService.deleteStatement(id);
        return ResponseEntity.ok("Declaração deletado com sucesso!");
    }
}
