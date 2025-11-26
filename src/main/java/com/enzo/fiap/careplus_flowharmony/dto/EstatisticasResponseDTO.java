package com.enzo.fiap.careplus_flowharmony.dto;

import java.math.BigDecimal;
import java.util.List;

public record EstatisticasResponseDTO(
        String periodoAnalise, // Ex: "Últimos 7 dias", "Mês Atual"
        List<DadosEstatisticos> dadosPorHabito
) {
    public record DadosEstatisticos(
            TipoHabitoResponseDTO tipoHabito,
            BigDecimal somaTotal,
            BigDecimal mediaDiaria,
            Long totalRegistros
    ) {}
}