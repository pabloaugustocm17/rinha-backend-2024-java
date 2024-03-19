package rinhabackend.api.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rinhabackend.api.models.Cliente;
import rinhabackend.api.repositories.ClienteRepository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ClienteService {

    private final ClienteRepository CLIENTE_REPOSITORY;

    public ClienteService(ClienteRepository clienteRepository) {
        this.CLIENTE_REPOSITORY = clienteRepository;
    }

    public Optional<Cliente> getCliente(Long id){

        return CLIENTE_REPOSITORY.findById(id);
    }

    @Async
    public CompletableFuture<Boolean> salvaUsuario(Cliente cliente){

        try{
            CLIENTE_REPOSITORY.saveAndFlush(cliente);
            return CompletableFuture.completedFuture(true);
        }catch (Exception e){
            return CompletableFuture.completedFuture(false);
        }

    }
}
