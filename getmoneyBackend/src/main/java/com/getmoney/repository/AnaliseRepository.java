package com.getmoney.repository;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.dto.response.EvolucaoMensalResponseDTO;
import com.getmoney.dto.response.ProgressoMetaResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnaliseRepository extends JpaRepository<Usuario,Integer>{

    /**
     * Retorna o total gasto por categoria de despesas para um usuário específico
     * Utilizado para montar gráfico de pizza
     * Filtra apenas categorias do tipo DESPESA
     * Considera transações e categorias ativas
     * Ordena do maior para o menor valor
     */
    @Query("SELECT c, SUM(t.valor) " +
            "FROM Transacao t " +
            "JOIN t.categoria c " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND c.tipo = 0 " +
            "AND t.status = 1 " +
            "AND c.status = 1 " +
            "GROUP BY c " +
            "ORDER BY SUM(t.valor) DESC")
    List<Object[]> listarCategoriasComTotalGastoPorUsuario(@Param("usuarioId") Integer usuarioId);

    /**
     * Retorna a evolução mensal de receitas e despesas
     * Ordena cronologicamente
     */
    @Query("SELECT YEAR(t.data), MONTH(t.data), " +
            "SUM(CASE WHEN c.tipo = 0 THEN t.valor ELSE 0 END), " +
            "SUM(CASE WHEN c.tipo = 1 THEN t.valor ELSE 0 END) " +
            "FROM Transacao t " +
            "JOIN t.categoria c " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.status = 1 " +
            "AND t.data BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY YEAR(t.data), MONTH(t.data) " +
            "ORDER BY YEAR(t.data), MONTH(t.data)")
    List<Object[]> listarEvolucaoMensalPorUsuario(
            @Param("usuarioId") Integer usuarioId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);


    /**
     * Retorna a meta e soma das transações com o progresso atual em porcentagem
     * Utilizado para mostrar barra de progresso
     */
    @Query("SELECT m, COALESCE(SUM(t.valor), 0) " +
            "FROM Meta m " +
            "LEFT JOIN m.transacoes t " +
            "WHERE m.usuario.id = :usuarioId " +
            "AND m.status = 1 " +
            "AND (t IS NULL OR t.status = 1) " +
            "GROUP BY m")
    List<Object[]> ListarMetasComProgressoPorUsuario(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT c FROM Categoria c WHERE c.usuario.id = :usuarioId AND c.status = :status")
    List<Categoria> findByUsuarioIdAndStatus(@Param("usuarioId") Integer usuarioId, @Param("status") Status status);
}