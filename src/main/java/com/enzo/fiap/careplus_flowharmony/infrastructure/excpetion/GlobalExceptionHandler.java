package com.enzo.fiap.careplus_flowharmony.infrastructure.excpetion;


import com.enzo.fiap.careplus_flowharmony.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * 1. Handler para ResponseStatusException (Ex: Usuário Não Encontrado 404)
     * Lida com exceções lançadas na camada Service (HabitoService) que definem um HttpStatus.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest request) {

        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String message = ex.getReason();
        String error = status.getReasonPhrase();

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                LocalDateTime.now().format(formatter),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorDTO, status);
    }

    /**
     * 2. Handler para MethodArgumentNotValidException (Erros de Validação @Valid)
     * Lida com falhas de validação dos DTOs de requisição (Controller).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        // Coleta todos os erros de campo
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Constrói a mensagem de erro detalhada
        String message = "Falha na validação dos campos: " + errors.toString();

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                LocalDateTime.now().format(formatter),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(errorDTO, status);
    }

    /**
     * 3. Handler Genérico (Fallback) para Exceções Não Capturadas (500 Internal Server Error)
     * Garante que toda exceção desconhecida seja tratada.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllUncaughtException(
            Exception ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                LocalDateTime.now().format(formatter),
                status.value(),
                status.getReasonPhrase(),
                "Um erro inesperado ocorreu. Detalhes: " + ex.getMessage(),
                request.getRequestURI()
        );

        // Opcional: Logar a exceção completa aqui
        // logger.error("Erro 500 não capturado:", ex);

        return new ResponseEntity<>(errorDTO, status);
    }
}
