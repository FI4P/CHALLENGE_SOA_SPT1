package com.enzo.fiap.careplus_flowharmony.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash; // Armazena o hash da senha

    @Column(name = "data_cadastro", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime dataCadastro;

    @Column(name = "status_conta")
    private String statusConta; // Ex: ATIVA, INATIVA
}