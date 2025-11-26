-- V1__create_initial_schema.sql
-- Migration inicial para o Care Plus - Flow Harmony, otimizado para PostgreSQL e JPA (Long/Integer).

-- 1. Tabela de Usuários
-- Mapeia para a Entidade Usuario (id: Long)
CREATE TABLE IF NOT EXISTS tb_usuario (
    id BIGSERIAL PRIMARY KEY, -- Mapeia para Long no Java/JPA
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    status_conta VARCHAR(50) DEFAULT 'ATIVA'
    );

-- 2. Tabela de Tipos de Hábito
-- Mapeia para a Entidade TipoHabito (id: Integer ou Long)
CREATE TABLE IF NOT EXISTS tb_tipo_habito (
                                              id SERIAL PRIMARY KEY, -- Mapeia para Integer ou Long no Java/JPA
                                              nome VARCHAR(100) UNIQUE NOT NULL,
    unidade_medida VARCHAR(50) NOT NULL -- Ex: "Litros", "Minutos", "Horas"
    );

-- Insere dados iniciais (Seed Data) para os tipos de hábitos
INSERT INTO tb_tipo_habito (nome, unidade_medida) VALUES
                                                      ('Hidratação', 'Litros'),
                                                      ('Qualidade do Sono', 'Horas'),
                                                      ('Meditação/Respiração', 'Minutos'),
                                                      ('Foco/Trabalho', 'Minutos'),
                                                      ('Pausa/Recuperação', 'Minutos');


-- 3. Tabela de Registro de Hábito (Núcleo do Flow Harmony)
-- Mapeia para a Entidade RegistroHabito (id: Long)
CREATE TABLE IF NOT EXISTS tb_registro_habito (
                                                  id BIGSERIAL PRIMARY KEY, -- Mapeia para Long no Java/JPA
                                                  usuario_id BIGINT NOT NULL, -- BIGINT: Foreign Key para tb_usuario(id)
                                                  tipo_habito_id INT NOT NULL, -- INT: Foreign Key para tb_tipo_habito(id)
                                                  valor_registro NUMERIC(5, 2) NOT NULL,
    data_registro DATE NOT NULL,
    hora_registro TIME WITHOUT TIME ZONE DEFAULT CURRENT_TIME,
    observacao VARCHAR(500),

    -- Chaves Estrangeiras
    CONSTRAINT fk_registro_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_registro_tipo_habito FOREIGN KEY (tipo_habito_id) REFERENCES tb_tipo_habito(id) ON DELETE RESTRICT
    );

-- 4. Tabela de Mapa de Energia Diário
-- Mapeia para a Entidade MapaEnergia (id: Long)
CREATE TABLE IF NOT EXISTS tb_mapa_energia (
                                               id BIGSERIAL PRIMARY KEY, -- Mapeia para Long no Java/JPA
                                               usuario_id BIGINT NOT NULL, -- BIGINT: Foreign Key para tb_usuario(id)
                                               data_registro DATE NOT NULL,
                                               nivel_energia SMALLINT CHECK (nivel_energia >= 1 AND nivel_energia <= 5) NOT NULL,
    comentario VARCHAR(255),

    -- Garante que o usuário só registre o mapa de energia uma vez por dia
    CONSTRAINT uk_energia_diaria UNIQUE (usuario_id, data_registro),

    -- Chave Estrangeira
    CONSTRAINT fk_energia_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id) ON DELETE CASCADE
    );
