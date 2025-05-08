package br.com.senai.reciclaville.controllers.clients;

import br.com.senai.reciclaville.models.entities.Clients;
import br.com.senai.reciclaville.services.clients.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Clients> create(@RequestBody Clients client) throws Exception{
        this.clientService.newClient(client);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<Clients>> findAllClients(){
        List<Clients> clients = this.clientService.findAllClients();
        return clients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clients);
    }

    @GetMapping("/id")
    public ResponseEntity<Clients> findClient(@PathVariable Long id){
        Optional<Clients> client = clientService.findClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/id")
    public ResponseEntity<Clients> update(@PathVariable Long id, @RequestBody Clients client) throws Exception{
        Clients updateClient = this.clientService.updateClient(id, client);
        return ResponseEntity.ok(updateClient);
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        this.clientService.deleteClient(id);
        return ResponseEntity.ok("Cliente deletado com sucesso!");
    }
}
