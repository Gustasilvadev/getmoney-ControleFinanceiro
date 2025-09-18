package com.getmoney.repository;

import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.status = -1 WHERE u.id = :id")
    void apagarUsuario(@Param("id")Integer usuarioId);

    @Query("SELECT u FROM Usuario u WHERE u.status >= 0")
    List<Usuario> listarUsuariosAtivos();

    @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.status >=0")
    Usuario findByUsuarioId(@Param("id")Integer usuarioId);

    UserDetails findByEmail(String email);

    Optional<Usuario> findUsuarioByEmail(String email);

}
