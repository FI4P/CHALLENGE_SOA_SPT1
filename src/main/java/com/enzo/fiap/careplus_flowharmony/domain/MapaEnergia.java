package com.enzo.fiap.careplus_flowharmony.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "tb_mapa_energia", uniqueConstraints = {
        // Garante a unicidade do registro (usuario_id, data_registro)
        @UniqueConstraint(columnNames = {"usuario_id", "data_registro"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapaEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "nivel_energia", nullable = false)
    private Integer nivelEnergia; // SMALLINT no DB

    private String comentario;

    // Relacionamento com Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}