package com.getmoney.repository;

import com.getmoney.entity.Meta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaRepository extends JpaRepository<Meta,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Meta m SET m.status = -1 WHERE m.id = :id AND m.usuario.id = :usuarioId")
    void apagarMeta(@Param("id") Integer metaId, @Param("usuarioId") Integer usuarioId);

    @Query("SELECT m FROM Meta m WHERE m.status >= 0 AND m.usuario.id = :usuarioId")
    List<Meta> listarMetasAtivas(@Param("usuarioId") Integer usuarioId);

    @Query("SELECT m FROM Meta m WHERE m.id = :id AND m.status >= 0 AND m.usuario.id = :usuarioId")
    Meta findByMetaIdAndUsuarioId(@Param("id") Integer metaId, @Param("usuarioId") Integer usuarioId);

    @Query("SELECT m FROM Meta m WHERE m.id IN :ids AND m.usuario.id = :usuarioId AND m.status = 1")
    List<Meta> findByIdInAndUsuarioId(@Param("ids") List<Integer> ids, @Param("usuarioId") Integer usuarioId);

    boolean existsByIdAndUsuarioId(Integer id, Integer usuarioId);

}
