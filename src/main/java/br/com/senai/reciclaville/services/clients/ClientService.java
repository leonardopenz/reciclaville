package br.com.senai.reciclaville.services.clients;

import br.com.senai.reciclaville.models.entities.Clients;
import br.com.senai.reciclaville.repositories.clients.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    //Cadastro de clientes
    public Clients newClient(Clients client){
        return clientRepository.save(client);
    }

    //Exibir todos os clientes
    public List<Clients> findAllClients(){
        return clientRepository.findAll();
    }

    //Exibir o cliente pelo id
    public Optional<Clients> findClientById(Long id){
        return clientRepository.findById(id);
    }

    //Atualizar o cadastro de um cliente
    public Clients updateClient(Long id, Clients updateClient) throws Exception{
        Optional<Clients> client = clientRepository.findById(id);
        if(client.isPresent()){
            client.get().setNome(updateClient.getNome());
            client.get().setCnpj(updateClient.getCnpj());
            client.get().setAtividadeEconomica(updateClient.getAtividadeEconomica());
            client.get().setResponsavel(updateClient.getResponsavel());
            clientRepository.save(client.get());
        }
        return client.get();
    }

    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }
}
