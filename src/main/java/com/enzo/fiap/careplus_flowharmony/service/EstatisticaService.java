package com.enzo.fiap.careplus_flowharmony.service;

import com.enzo.fiap.careplus_flowharmony.dto.EstatisticasResponseDTO;
import com.enzo.fiap.careplus_flowharmony.dto.TipoHabitoResponseDTO;
import com.enzo.fiap.careplus_flowharmony.dto.EstatisticasResponseDTO.DadosEstatisticos;

import com.enzo.fiap.careplus_flowharmony.repository.RegistroHabitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstatisticaService {

    @Autowired
    private RegistroHabitoRepository registroHabitoRepository;

    /**
     * Lógica de Negócio: Calcula a soma e média de registros por tipo de hábito em um período.
     * @param usuarioId ID do usuário logado.
     * @param diasAnteriores Número de dias para trás a partir da data atual (ex: 7 para última semana).
     * @return EstatisticasResponseDTO contendo os dados agregados.
     */
    @Transactional(readOnly = true)
    public EstatisticasResponseDTO calcularEstatisticasUltimosDias(Long usuarioId, int diasAnteriores) {
        LocalDate dataFinal = LocalDate.now();
        LocalDate dataInicial = dataFinal.minusDays(diasAnteriores - 1); // Ex: 7 dias inclui hoje

        List<Object[]> resultadosRaw = registroHabitoRepository
                .calcularEstatisticasPorPeriodo(usuarioId, dataInicial, dataFinal);

        List<DadosEstatisticos> dadosFormatados = resultadosRaw.stream()
                .map(this::mapRawToDadosEstatisticos)
                .collect(Collectors.toList());

        String periodo = String.format("Últimos %d dias (de %s a %s)", diasAnteriores, dataInicial.toString(), dataFinal.toString());

        return new EstatisticasResponseDTO(periodo, dadosFormatados);
    }

    // Método privado para mapear o resultado bruto (Object[]) da Query para o DTO
    private DadosEstatisticos mapRawToDadosEstatisticos(Object[] raw) {
        // O mapeamento segue a ordem definida na query JPQL no RegistroHabitoRepository:
        // 0: nomeHabito (String), 1: unidade (String), 2: somaTotal (BigDecimal/Double), 3: mediaDiaria (Double), 4: totalRegistros (Long/BigInteger)

        String nomeHabito = (String) raw[0];
        String unidade = (String) raw[1];

        // Conversões seguras de tipos numéricos
        BigDecimal somaTotal = new BigDecimal(raw[2].toString());
        BigDecimal mediaDiaria = new BigDecimal(raw[3].toString());

        Long totalRegistros = ((BigInteger) raw[4]).longValue(); // Conversão de BigInteger para Long

        TipoHabitoResponseDTO tipoHabitoDTO = new TipoHabitoResponseDTO(null, nomeHabito, unidade);

        return new DadosEstatisticos(
                tipoHabitoDTO,
                somaTotal,
                mediaDiaria,
                totalRegistros
        );
    }
}