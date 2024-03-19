package rinhabackend.api.models.dtos;

import java.time.LocalDateTime;

public record SaldoDTO(
        Long total,
        LocalDateTime data_extrato,
        Long limite
){ }
