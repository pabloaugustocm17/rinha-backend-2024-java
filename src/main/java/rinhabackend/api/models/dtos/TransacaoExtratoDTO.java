package rinhabackend.api.models.dtos;

import java.time.LocalDateTime;

public record TransacaoExtratoDTO(
        Long valor,
        String tipo,
        String descricao,
        LocalDateTime realizada_em
){}
