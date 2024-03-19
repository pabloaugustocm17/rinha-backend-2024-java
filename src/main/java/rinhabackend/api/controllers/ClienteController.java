package rinhabackend.api.controllers;

import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rinhabackend.api.models.Cliente;
import rinhabackend.api.models.TipoTransacao;
import rinhabackend.api.models.Transacao;
import rinhabackend.api.models.dtos.ExtratoDTO;
import rinhabackend.api.models.dtos.SaldoDTO;
import rinhabackend.api.models.dtos.TransacaoDTO;
import rinhabackend.api.models.dtos.TransacaoExtratoDTO;
import rinhabackend.api.services.ClienteService;
import rinhabackend.api.services.TransacaoService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static rinhabackend.api.utils.Dictionary.*;

@RestController
@RequestMapping("/clientes/")
public class ClienteController {

    private final ClienteService CLIENTE_SERVICE;

    private final TransacaoService TRANSACAO_SERVICE;

    public ClienteController(ClienteService clienteService, TransacaoService transacaoService) {
        this.CLIENTE_SERVICE = clienteService;
        this.TRANSACAO_SERVICE = transacaoService;
    }

    @PostMapping(
            value = "{id}/transacoes",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> criaTransacao(
            @NonNull @PathVariable Long id,
            @NonNull @RequestBody TransacaoDTO dto
    ){

        String tipo = dto.tipo();

        String descricao = dto.descricao();

        if(descricao == null || descricao.isEmpty() || descricao.isBlank()){
            return ResponseEntity.status(400).body("");
        }

        Long valor = dto.valor();

        if((!tipo.equalsIgnoreCase(CREDITO) && !tipo.equalsIgnoreCase(DEBITO)) || descricao.length() > 10
        || valor <= 0){
            return ResponseEntity.status(400).body("");
        }

        Optional<Cliente> optional_cliente = CLIENTE_SERVICE.getCliente(id);

        if(optional_cliente.isEmpty()){
            return ResponseEntity.status(404).body("");
        }

        Cliente cliente = optional_cliente.get();

        BigInteger saldo = cliente.getSaldo();
        BigInteger limite = cliente.getLimite();

        if(tipo.equalsIgnoreCase(DEBITO)){
            saldo = saldo.subtract(BigInteger.valueOf(valor));

            if(saldo.longValue() * -1 > limite.longValue()){
                return ResponseEntity.status(422).body("");
            }
        }else{
            saldo = saldo.add(BigInteger.valueOf(valor));
        }

        cliente.setSaldo(saldo);

        CompletableFuture<Boolean> transacao_salva = TRANSACAO_SERVICE.criarTransacao(dto, cliente);
        CompletableFuture<Boolean> cliente_salvo = CLIENTE_SERVICE.salvaUsuario(cliente);

        CompletableFuture.allOf(transacao_salva, cliente_salvo).join();

        HashMap<String, Integer> limite_saldo = new HashMap<>();

        limite_saldo.put("limite", limite.intValue());
        limite_saldo.put("saldo", saldo.intValue());

        return ResponseEntity.status(200).body(limite_saldo);
    }

    @GetMapping("{id}/extrato")
    public ResponseEntity<?> retornaExtrato(
            @NonNull @PathVariable Long id
    ){

        Optional<Cliente> optional_cliente = CLIENTE_SERVICE.getCliente(id);

        if(optional_cliente.isEmpty()){
            return ResponseEntity.status(404).body("");
        }

        Cliente cliente = optional_cliente.get();

        SaldoDTO saldo_dto = new SaldoDTO(cliente.getSaldo().longValue(),
                LocalDateTime.now(), cliente.getLimite().longValue());

        List<Transacao> transacoes = TRANSACAO_SERVICE.retornaTransacoes(cliente);

        if(transacoes.isEmpty()){
            return ResponseEntity.ok(new ExtratoDTO(saldo_dto, null));
        }

        List<TransacaoExtratoDTO> transacaoExtratoDTOS = new ArrayList<>(10);

        transacoes.forEach(transacao -> {
            TransacaoExtratoDTO dto =
                    new TransacaoExtratoDTO(
                            transacao.getValor().longValue(),
                            String.valueOf(transacao.getTipo()),
                            transacao.getDescricao(),
                            transacao.getRealizada_em()
                    );
            transacaoExtratoDTOS.add(dto);
        });

        ExtratoDTO dto = new ExtratoDTO(saldo_dto, transacaoExtratoDTOS);

        return ResponseEntity.ok(dto);


    }

}
