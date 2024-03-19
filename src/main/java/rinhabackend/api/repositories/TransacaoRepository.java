package rinhabackend.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rinhabackend.api.models.Cliente;
import rinhabackend.api.models.Transacao;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT T " +
            "FROM Transacao AS T " +
            "WHERE T.cliente_id.id =: cliente " +
            "ORDER BY T.realizada_em DESC " +
            "LIMIT 10")
    List<Transacao> retornaUltimasTransacoes(
            @Param("cliente")Long cliente
    );
}
