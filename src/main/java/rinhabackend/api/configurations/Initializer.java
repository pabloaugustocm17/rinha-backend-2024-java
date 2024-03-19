package rinhabackend.api.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import rinhabackend.api.models.Cliente;
import rinhabackend.api.repositories.ClienteRepository;

@Configuration
public class Initializer {

    /*private final ClienteRepository CLIENTE_REPOSITORY;

    private final Logger LOGGER;

    public Initializer(ClienteRepository clienteRepository){
        this.CLIENTE_REPOSITORY = clienteRepository;
        LOGGER = LoggerFactory.getLogger(this.getClass());
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onInitialize(){

        Cliente cliente1 = new Cliente(100_000);
        Cliente cliente2 = new Cliente(80_000);
        Cliente cliente3 = new Cliente(1_000_000);
        Cliente cliente4 = new Cliente(10_000_000);
        Cliente cliente5 = new Cliente(500_000);

        CLIENTE_REPOSITORY.save(cliente1);
        CLIENTE_REPOSITORY.save(cliente2);
        CLIENTE_REPOSITORY.save(cliente3);
        CLIENTE_REPOSITORY.save(cliente4);
        CLIENTE_REPOSITORY.save(cliente5);

        LOGGER.info("Criação de clientes iniciais concluído...");
    }*/

}
