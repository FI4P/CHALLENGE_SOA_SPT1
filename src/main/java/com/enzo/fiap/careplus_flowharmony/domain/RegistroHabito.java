package com.enzo.fiap.careplus_flowharmony.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_registro_habito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_registro", precision = 5, scale = 2, nullable = false)
    private BigDecimal valorRegistro;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "hora_registro")
    private LocalTime horaRegistro;

    private String observacao;

    // Relacionamento com Usuario (Muitos registros para Um usuário)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relacionamento com TipoHabito (Muitos registros para Um tipo de hábito)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_habito_id", nullable = false)
    private TipoHabito tipoHabito;
}