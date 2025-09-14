package com.getmoney.repository;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
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
    @Query("UPDATE Categoria c SET c.status = -1 WHERE c.id = :id")
    void apagarCatgoria(@Param("id")Integer categoriaId);

    @Query("SELECT c FROM Categoria c WHERE c.status > 0")
    List<Categoria> listarCategoriasAtivas();


    /**
     * Busca categorias distintas associadas a transações de um usuário específico.
     */
    @Query("SELECT DISTINCT c FROM Categoria c JOIN c.transacoes t WHERE t.usuario.id = :usuarioId ")
    List<Categoria> buscarCategoriasPorUsuarioId(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT c FROM Categoria c WHERE c.id = :id AND c.status >=0")
    Categoria ObterCategoriaPeloId(@Param("id")Integer categoriaId);

    List<Categoria> findByTipo(CategoriaTipo tipo);

    /**
     * Busca categorias pelo nome, considerando apenas as com status maior ou igual a zero.
     */
    @Query("SELECT c FROM Categoria c WHERE c.nome LIKE %:nome% AND c.status > 0")
    List<Categoria> buscarPorCategoriaNome(@Param("nome") String nome);


}
