package com.getmoney.repository;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.dto.response.EvolucaoMensalResponseDTO;
import com.getmoney.dto.response.ProgressoMetaResponseDTO;
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

    // Resumo de categorias (gráfico de pizza)
    @Query("SELECT c, SUM(t.valor) " +
            "FROM Transacao t " +
            "JOIN t.categoria c " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND c.tipo = :tipoDespesa " +
            "AND t.status = :statusAtivo " +
            "AND c.status = :statusAtivo " +
            "GROUP BY c " +
            "ORDER BY SUM(t.valor) DESC")
    List<Object[]> listarCategoriasComTotalGastoPorUsuario(
            @Param("usuarioId") Integer usuarioId,
            @Param("tipoDespesa") CategoriaTipo tipoDespesa,
            @Param("statusAtivo") Status statusAtivo);

    // Evolução mensal (gráfico de linha)
    @Query("SELECT YEAR(t.data), MONTH(t.data), " +
            "SUM(CASE WHEN c.tipo = :tipoDespesa THEN t.valor ELSE 0 END), " +
            "SUM(CASE WHEN c.tipo = :tipoReceita THEN t.valor ELSE 0 END) " +
            "FROM Transacao t " +
            "JOIN t.categoria c " +
            "WHERE t.usuario.id = :usuarioId " +
            "AND t.status = :statusAtivo " +
            "AND t.data BETWEEN :dataInicio AND :dataFim " +
            "GROUP BY YEAR(t.data), MONTH(t.data) " +
            "ORDER BY YEAR(t.data), MONTH(t.data)")
    List<Object[]> listarEvolucaoMensalPorUsuario(
            @Param("usuarioId") Integer usuarioId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("tipoDespesa") CategoriaTipo tipoDespesa,
            @Param("tipoReceita") CategoriaTipo tipoReceita,
            @Param("statusAtivo") Status statusAtivo);

    // Progresso das metas
    @Query("SELECT m, COALESCE(SUM(t.valor), 0) " +
            "FROM Meta m " +
            "LEFT JOIN m.transacoes t " +
            "WHERE m.usuario.id = :usuarioId " +
            "AND m.status = :statusAtivo " +  // Use o enum diretamente
            "AND (t IS NULL OR t.status = :statusAtivo) " +  // Use o enum diretamente
            "GROUP BY m")
    List<Object[]> ListarMetasComProgressoPorUsuario(
            @Param("usuarioId") Integer usuarioId,
            @Param("statusAtivo") Status statusAtivo);  // Recebe o enum como parâmetro
}