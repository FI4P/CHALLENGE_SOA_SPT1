package com.enzo.fiap.careplus_flowharmony.domain;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tb_tipo_habito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoHabito {

    // Mapeado para SERIAL (Integer/Long) no DB
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome; // Ex: Hidratação, Sono

    @Column(name = "unidade_medida", nullable = false)
    private String unidadeMedida; // Ex: Litros, Horas, Minutos
}
