package rinhabackend.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Table(name = "tb_cliente")
@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger limite;

    private BigInteger saldo;

    public Cliente(int limite) {
        this.limite = BigInteger.valueOf(limite);
        this.saldo = BigInteger.ZERO;
    }
}
