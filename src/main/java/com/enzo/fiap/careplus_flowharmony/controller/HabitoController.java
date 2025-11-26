package com.enzo.fiap.careplus_flowharmony.controller;


import com.enzo.fiap.careplus_flowharmony.dto.EstatisticasResponseDTO;
import com.enzo.fiap.careplus_flowharmony.dto.RegistroHabitoRequestDTO;
import com.enzo.fiap.careplus_flowharmony.dto.RegistroHabitoResponseDTO;
import com.enzo.fiap.careplus_flowharmony.service.EstatisticaService;
import com.enzo.fiap.careplus_flowharmony.service.HabitoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/habitos") // Endpoint Raiz para todos os serviços de hábito
public class HabitoController {

    @Autowired
    private HabitoService habitoService;

    @Autowired
    private EstatisticaService estatisticaService;

    // TODO: Em um projeto real, o ID do usuário seria extraído do token JWT
    // Por enquanto, simulamos o ID do usuário (Ex: Usuário Ana Silva = 1)
    private static final Long USUARIO_ID_SIMULADO = 3L;

    // ------------------------------------------
    // Endpoint: Registrar Hábito
    // Método HTTP: POST
    // URL: /api/habitos/registro
    // ------------------------------------------

    /**
     * POST /api/habitos/registro
     * Registra um novo hábito não clínico (ex: Hidratação, Meditação).
     * @param dto O DTO de requisição contendo o tipo e o valor do registro.
     * @return O registro salvo com status 201 Created.
     */
    @PostMapping("/registro")
    public ResponseEntity<RegistroHabitoResponseDTO> registrarHabito(
            @RequestBody @Valid RegistroHabitoRequestDTO dto) {

        // 1. Validação de Entrada (feita pelo @Valid e DTO)
        // 2. Chamada da Camada de Serviço (Lógica de Negócio)
        RegistroHabitoResponseDTO response = habitoService.registrarHabito(USUARIO_ID_SIMULADO, dto);

        // 3. Retorno REST (Código 201 CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ------------------------------------------
    // Endpoint: Consultar Registros (Histórico)
    // Método HTTP: GET
    // URL: /api/habitos/registros
    // ------------------------------------------

    /**
     * GET /api/habitos/registros?dataInicial=...&dataFinal=...
     * Lista todos os registros de hábitos do usuário em um período.
     * @param dataInicial Data de início do período (formato YYYY-MM-DD).
     * @param dataFinal Data de fim do período (formato YYYY-MM-DD).
     * @return Lista de registros com status 200 OK.
     */
    @GetMapping("/registros")
    public ResponseEntity<List<RegistroHabitoResponseDTO>> buscarHistorico(
            @RequestParam @Valid LocalDate dataInicial,
            @RequestParam @Valid LocalDate dataFinal) {

        List<RegistroHabitoResponseDTO> historico = habitoService.buscarHistorico(USUARIO_ID_SIMULADO, dataInicial, dataFinal);

        // 4. Tratamento adequado de erros e exceções: o Service lança NOT_FOUND, que é capturado
        // automaticamente pelo Spring em caso de falha. Se vazio, retorna 200 OK com lista vazia.
        return ResponseEntity.ok(historico);
    }

    // ------------------------------------------
    // Endpoint: Exibir Estatísticas
    // Método HTTP: GET
    // URL: /api/habitos/estatisticas
    // ------------------------------------------

    /**
     * GET /api/habitos/estatisticas?dias=7
     * Calcula a soma total e a média dos valores informados nos últimos N dias.
     * @param dias Número de dias para trás a partir de hoje (padrão é 7).
     * @return Estatísticas agregadas por tipo de hábito com status 200 OK.
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasResponseDTO> exibirEstatisticas(
            @RequestParam(defaultValue = "7") int dias) {

        EstatisticasResponseDTO estatisticas = estatisticaService.calcularEstatisticasUltimosDias(USUARIO_ID_SIMULADO, dias);

        return ResponseEntity.ok(estatisticas);
    }
}
