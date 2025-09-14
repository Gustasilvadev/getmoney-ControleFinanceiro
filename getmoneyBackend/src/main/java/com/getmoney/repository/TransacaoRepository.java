package com.getmoney.repository;

import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Transacao t SET t.status = -1 WHERE t.id = :id")
    void apagarTransacao(@Param("id")Integer transacaoId);

    @Query("SELECT t FROM Transacao t WHERE t.status > 0")
    List<Transacao> listarTransacoesAtivas();

    @Query("SELECT t FROM Transacao t WHERE t.id = :id AND t.status >=0")
    Transacao ObterTransacaoPeloId(@Param("id")Integer transacaoId);

    /**
     * Soma todos os valores das transações do tipo Receita (tipo = 1)
     * associadas ao ID do usuário fornecido.
     */
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
            "JOIN t.categoria c WHERE t.usuario.id = :usuarioId AND c.tipo = 1")
    BigDecimal TotalReceitas(@Param("usuarioId") Integer usuarioId);

    /**
     * Soma todos os valores das transações do tipo Despesa (tipo = 0)
     * associadas ao ID do usuário fornecido.
     */
    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t " +
            "JOIN t.categoria c WHERE t.usuario.id = :usuarioId AND c.tipo = 0")
    BigDecimal TotalDespesas(@Param("usuarioId") Integer usuarioId);

    /**
     * Lista todas as transacoes da meta
     */
    @Query("SELECT t FROM Transacao t JOIN t.metas m WHERE m.id = :metaId AND t.status > 0")
    List<Transacao> ListarTransacaoPorMetaId(@Param("metaId") Integer metaId);

    /**
     * Lista todas as transacoes da categoria
     */
    @Query("SELECT t FROM Transacao t WHERE t.categoria.id = :categoriaId AND t.status > 0")
    List<Transacao> listarTransacoesAtivasPorCategoriaId(@Param("categoriaId") Integer categoriaId);

    /**
     * Busca uma transação específica pelo seu ID e pelo ID de uma meta associada, considerando apenas transações ativas.
     */
    @Query("SELECT t FROM Transacao t JOIN t.metas m WHERE t.id = :id AND m.id = :metaId AND t.status > 0")
    Transacao listarTransacaoIdEMetaId(@Param("id") Integer id, @Param("metaId") Integer metaId);

    /**
     * Busca uma transação específica pelo seu ID e pelo ID de sua categoria, considerando apenas transações ativas.
     */
    @Query("SELECT t FROM Transacao t " +
            "WHERE t.id = :id " +
            "AND t.categoria.id = :categoriaId " +
            "AND t.usuario.id = :usuarioId " +
            "AND t.status > 0")
    Transacao listarTransacaoIdECategoriaId(
            @Param("id") Integer id,
            @Param("categoriaId") Integer categoriaId,
            @Param("usuarioId") Integer usuarioId);
}