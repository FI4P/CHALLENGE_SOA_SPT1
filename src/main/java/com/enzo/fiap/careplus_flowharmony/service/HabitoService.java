package com.enzo.fiap.careplus_flowharmony.service;

import com.enzo.fiap.careplus_flowharmony.domain.RegistroHabito;
import com.enzo.fiap.careplus_flowharmony.domain.TipoHabito;
import com.enzo.fiap.careplus_flowharmony.domain.Usuario;
import com.enzo.fiap.careplus_flowharmony.dto.RegistroHabitoRequestDTO;
import com.enzo.fiap.careplus_flowharmony.dto.RegistroHabitoResponseDTO;
import com.enzo.fiap.careplus_flowharmony.dto.TipoHabitoResponseDTO;
import com.enzo.fiap.careplus_flowharmony.repository.RegistroHabitoRepository;
import com.enzo.fiap.careplus_flowharmony.repository.TipoHabitoRepository;
import com.enzo.fiap.careplus_flowharmony.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class HabitoService {

    @Autowired
    private RegistroHabitoRepository registroHabitoRepository;

    @Autowired
    private TipoHabitoRepository tipoHabitoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Usado para simular a busca do usuário logado

    /**
     * Lógica de Negócio: Salva um novo registro de hábito a partir do DTO de requisição.
     * @param usuarioId ID do usuário logado (simulado).
     * @param dto Dados do novo registro.
     * @return RegistroHabitoResponseDTO do objeto salvo.
     */
    @Transactional
    public RegistroHabitoResponseDTO registrarHabito(Long usuarioId, RegistroHabitoRequestDTO dto) {
        // 1. Validar e Buscar o Usuário (Simulação de Segurança)
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado."));

        // 2. Validar e Buscar o Tipo de Hábito
        TipoHabito tipoHabito = tipoHabitoRepository.findById(dto.tipoHabitoId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Tipo de Hábito não encontrado."));

        // 3. Criar a Entidade
        RegistroHabito novoRegistro = new RegistroHabito();
        novoRegistro.setUsuario(usuario);
        novoRegistro.setTipoHabito(tipoHabito);
        novoRegistro.setValorRegistro(dto.valorRegistro());
        novoRegistro.setObservacao(dto.observacao());
        // Define data e hora do registro no momento da operação (boa prática)
        novoRegistro.setDataRegistro(LocalDate.now());
        // A horaRegistro é opcional, mas pode ser definida aqui se o DB não tiver DEFAULT

        // 4. Salvar no DB
        RegistroHabito salvo = registroHabitoRepository.save(novoRegistro);

        // 5. Converter e Retornar DTO de Resposta
        return toResponseDTO(salvo);
    }

    /**
     * Lógica de Negócio: Busca o histórico de registros do usuário em um período.
     * @param usuarioId ID do usuário logado.
     * @param dataInicial Início do período.
     * @param dataFinal Fim do período.
     * @return Lista de RegistroHabitoResponseDTO.
     */
    @Transactional(readOnly = true)
    public List<RegistroHabitoResponseDTO> buscarHistorico(Long usuarioId, LocalDate dataInicial, LocalDate dataFinal) {
        // Busca direta no Repositório
        List<RegistroHabito> registros = registroHabitoRepository
                .findByUsuarioIdAndDataRegistroBetween(usuarioId, dataInicial, dataFinal);

        // Converte a lista de Entidades para lista de DTOs de Resposta
        return registros.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Método privado para mapear Entidade -> DTO de Resposta (Mappers são comuns em SOA)
    private RegistroHabitoResponseDTO toResponseDTO(RegistroHabito registro) {
        TipoHabitoResponseDTO tipoHabitoDTO = new TipoHabitoResponseDTO(
                registro.getTipoHabito().getId(),
                registro.getTipoHabito().getNome(),
                registro.getTipoHabito().getUnidadeMedida()
        );

        return new RegistroHabitoResponseDTO(
                registro.getId(),
                registro.getValorRegistro(),
                registro.getDataRegistro(),
                registro.getHoraRegistro(),
                registro.getObservacao(),
                tipoHabitoDTO
        );
    }
}