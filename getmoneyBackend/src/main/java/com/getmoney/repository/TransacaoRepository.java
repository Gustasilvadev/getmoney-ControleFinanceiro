package com.getmoney.repository;

import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Transacao t SET t.status = -1 WHERE t.id = :id")
    void apagarTransacao(@Param("id")Integer transacaoId);

    @Query("SELECT t FROM Transacao t WHERE t.status >= 0")
    List<Transacao> listarTransacoesAtivas();

    @Query("SELECT t FROM Transacao t WHERE t.id = :id AND t.status >=0")
    Transacao ObterTransacaoPeloId(@Param("id")Integer transacaoId);

}
