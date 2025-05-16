package br.com.senai.reciclaville.controllers.clients;

import br.com.senai.reciclaville.models.DTOS.clients.ClientRequestDTO;
import br.com.senai.reciclaville.models.DTOS.clients.ClientResponseDTO;
import br.com.senai.reciclaville.models.entities.Clients;
import br.com.senai.reciclaville.services.clients.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientRequestDTO clientDTO ) throws Exception{
        Clients client = modelMapper.map(clientDTO, Clients.class);
        Clients createdClient = clientService.newClient(client);
        ClientResponseDTO createdClientDTO = modelMapper.map(createdClient, ClientResponseDTO.class);

        return ResponseEntity.ok(createdClientDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> findAllClients(){
        List<ClientResponseDTO> clients = this.clientService.findAllClients().stream()
                .map(client -> modelMapper.map(client, ClientResponseDTO.class))
                .collect(Collectors.toList());
        return clients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findClient(@PathVariable Long id) {
        Optional<Clients> client = clientService.findClientById(id);

        return client.map(clients -> {
                    ClientResponseDTO clientDto = modelMapper.map(clients, ClientResponseDTO.class);
                    return ResponseEntity.ok(clientDto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @RequestBody ClientResponseDTO clientDTO) throws Exception{
        Clients client = modelMapper.map(clientDTO, Clients.class);
        Clients updateClient = this.clientService.updateClient(id, client);
        ClientResponseDTO updateClientDTO = modelMapper.map(updateClient, ClientResponseDTO.class);

        return ResponseEntity.ok(updateClientDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        this.clientService.deleteClient(id);
        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }
}
