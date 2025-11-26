package com.enzo.fiap.careplus_flowharmony.repository;

import com.enzo.fiap.careplus_flowharmony.domain.RegistroHabito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroHabitoRepository extends JpaRepository<RegistroHabito, Long> {

    // 1. Método para listar todos os registros de um usuário em um período (para a tela de histórico)
    List<RegistroHabito> findByUsuarioIdAndDataRegistroBetween(
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal
    );

    // 2. Método para calcular estatísticas (média e soma) agrupadas por tipo de hábito
    // Esta é a query chave para o endpoint de estatísticas
    @Query("""
        SELECT
            r.tipoHabito.nome AS nomeHabito,
            r.tipoHabito.unidadeMedida AS unidade,
            SUM(r.valorRegistro) AS somaTotal,
            AVG(r.valorRegistro) AS mediaDiaria,
            COUNT(r.id) AS totalRegistros
        FROM RegistroHabito r
        WHERE r.usuario.id = :usuarioId AND r.dataRegistro BETWEEN :dataInicial AND :dataFinal
        GROUP BY r.tipoHabito.id, r.tipoHabito.nome, r.tipoHabito.unidadeMedida
        ORDER BY r.tipoHabito.nome
    """)
    List<Object[]> calcularEstatisticasPorPeriodo(
            @Param("usuarioId") Long usuarioId,
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal
    );

}