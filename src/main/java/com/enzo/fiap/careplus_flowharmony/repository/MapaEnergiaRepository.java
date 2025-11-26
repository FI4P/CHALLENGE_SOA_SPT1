package com.enzo.fiap.careplus_flowharmony.repository;
import com.enzo.fiap.careplus_flowharmony.domain.MapaEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MapaEnergiaRepository extends JpaRepository<MapaEnergia, Long> {

    // Método para verificar se o usuário já registrou o mapa de energia no dia (regra de negócio: uma vez por dia)
    Optional<MapaEnergia> findByUsuarioIdAndDataRegistro(Long usuarioId, LocalDate dataRegistro);

}