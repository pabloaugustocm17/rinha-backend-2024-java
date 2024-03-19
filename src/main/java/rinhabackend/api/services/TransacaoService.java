package rinhabackend.api.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rinhabackend.api.models.Cliente;
import rinhabackend.api.models.Transacao;
import rinhabackend.api.models.dtos.TransacaoDTO;
import rinhabackend.api.repositories.TransacaoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransacaoService {

    private final TransacaoRepository TRANSACAO_REPOSITORY;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.TRANSACAO_REPOSITORY = transacaoRepository;
    }

    @Async
    public CompletableFuture<Boolean> criarTransacao(TransacaoDTO dto, Cliente cliente){

        Transacao transacao = new Transacao(dto, cliente);

        try{
            TRANSACAO_REPOSITORY.save(transacao);
            return CompletableFuture.completedFuture(true);
        }catch (Exception e){
            return CompletableFuture.completedFuture(false);
        }
    }

    public List<Transacao> retornaTransacoes(Cliente cliente){

        try{
            return TRANSACAO_REPOSITORY.retornaUltimasTransacoes(cliente.getId());
        }catch (Exception e){
            return new ArrayList<>();
        }

    }

}
