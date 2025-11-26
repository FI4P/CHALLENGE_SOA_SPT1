package com.enzo.fiap.careplus_flowharmony.repository;

import com.enzo.fiap.careplus_flowharmony.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
