package com.enzo.fiap.careplus_flowharmony.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MapaEnergiaRequestDTO(
        // Escala de energia/humor de 1 a 5 estrelas.
        @NotNull(message = "O nível de energia é obrigatório.")
        @Min(value = 1, message = "O nível mínimo de energia é 1.")
        @Max(value = 5, message = "O nível máximo de energia é 5.")
        Integer nivelEnergia,

        // Comentário opcional sobre o dia ou humor.
        String comentario
) { }
