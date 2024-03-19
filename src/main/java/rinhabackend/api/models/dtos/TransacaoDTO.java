package rinhabackend.api.models.dtos;

import lombok.NonNull;

public record TransacaoDTO(
        @NonNull Long valor,
        @NonNull String tipo,
        String descricao
) {
}
