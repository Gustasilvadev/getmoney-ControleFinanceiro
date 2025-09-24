package com.getmoney.repository;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Categoria c SET c.status = -1 WHERE c.id = :id AND c.usuario.id = :usuarioId")
    void apagarCatgoria(@Param("id") Integer categoriaId, @Param("usuarioId") Integer usuarioId);


    @Query("SELECT DISTINCT c FROM Categoria c " +
            "JOIN c.transacoes t " +
            "WHERE c.status = 1 AND c.usuario.id = :usuarioId AND t.usuario.id = :usuarioId")
    List<Categoria> listarCategoriasAtivasPorUsuario(@Param("usuarioId") Integer usuarioId);


    /**
     * Busca categorias distintas associadas a transações de um usuário específico.
     */
    @Query("SELECT DISTINCT c FROM Categoria c JOIN c.transacoes t WHERE t.usuario.id = :usuarioId AND c.usuario.id = :usuarioId AND c.status = 1")
    List<Categoria> buscarCategoriasPorUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT c FROM Categoria c WHERE c.id = :id AND c.status = 1 AND c.usuario.id = :usuarioId")
    Categoria findByCategoriaId(@Param("id") Integer categoriaId, @Param("usuarioId") Integer usuarioId);

    @Query("SELECT c FROM Categoria c WHERE c.tipo = :tipo AND c.usuario.id = :usuarioId AND c.status = 1")
    List<Categoria> findByTipo(@Param("tipo") CategoriaTipo tipo, @Param("usuarioId") Integer usuarioId);

    /**
     * Busca categorias pelo nome, considerando apenas as com status maior ou igual a zero.
     */
    @Query("SELECT c FROM Categoria c WHERE c.nome LIKE %:nome% AND c.status = 1 AND c.usuario.id = :usuarioId")
    List<Categoria> findByCategoriaNome(@Param("nome") String nome, @Param("usuarioId") Integer usuarioId);


    @Query("SELECT c FROM Categoria c WHERE c.usuario.id = :usuarioId AND c.status = :status")
    List<Categoria> findByUsuarioIdAndStatus(@Param("usuarioId") Integer usuarioId, @Param("status") Status status);


}
