package com.enzo.fiap.careplus_flowharmony.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroHabitoResponseDTO(
        Long id,
        BigDecimal valorRegistro,
        LocalDate dataRegistro,
        LocalTime horaRegistro,
        String observacao,

        // Inclui o DTO do Tipo de Hábito para evitar retornar a FK bruta
        TipoHabitoResponseDTO tipoHabito
) { }