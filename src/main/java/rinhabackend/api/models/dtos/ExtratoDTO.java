package rinhabackend.api.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record ExtratoDTO(
        SaldoDTO saldo,
        List<TransacaoExtratoDTO> ultimas_transacoes
) { }