package rinhabackend.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import rinhabackend.api.models.dtos.TransacaoDTO;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Table(name = "tb_transacao")
@Entity
@Data
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger valor;

    private char tipo;

    private LocalDateTime realizada_em;

    @Column(length = 10)
    private String descricao;

    @ManyToOne(targetEntity = Cliente.class)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente_id;

    public Transacao(TransacaoDTO dto, Cliente cliente) {
        this.valor = BigInteger.valueOf(dto.valor());
        this.tipo = dto.tipo().charAt(0);
        this.descricao = dto.descricao();
        this.cliente_id = cliente;
    }
}

