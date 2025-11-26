package com.enzo.fiap.careplus_flowharmony.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegistroHabitoRequestDTO(
        // ID do tipo de hábito (ex: 1 para Hidratação, 2 para Sono).
        // O backend usará este ID para buscar o TipoHabito no DB.
        @NotNull(message = "O ID do tipo de hábito é obrigatório.")
        Long tipoHabitoId,

        // O valor registrado pelo usuário (ex: 2.5 litros, 8.0 horas).
        @NotNull(message = "O valor do registro é obrigatório.")
        @Positive(message = "O valor deve ser positivo.")
        BigDecimal valorRegistro,

        // Observação opcional do usuário.
        String observacao
) {
    // Nota: O usuario_id será capturado pelo token de segurança (futuramente)
    // ou por um cabeçalho, não diretamente no corpo da requisição.
    // A data será a data do sistema por padrão, a menos que especificado.
}
