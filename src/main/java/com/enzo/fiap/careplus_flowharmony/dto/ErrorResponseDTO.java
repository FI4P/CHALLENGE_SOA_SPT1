package com.enzo.fiap.careplus_flowharmony.dto;

public record ErrorResponseDTO(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {}
